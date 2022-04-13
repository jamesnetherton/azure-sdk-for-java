// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.http.vertx.implementation.VertxHttpRequest;
import com.azure.core.http.vertx.implementation.VertxHttpResponseHandler;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Closeable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * {@link HttpClient} implementation for the Vert.x {@link WebClient}.
 */
public class VertxAsyncHttpClient implements HttpClient, Closeable {

    private static final ClientLogger LOGGER = new ClientLogger(VertxAsyncHttpClient.class);
    private final Vertx vertx;
    private final WebClient client;
    private boolean managedVertx;
    final WebClientOptions webClientOptions;

    /**
     * Constructs a {@link VertxAsyncHttpClient}.
     *
     * @param vertx The {@link Vertx}
     * @param options The {@link WebClientOptions} to configure the {@link WebClient} with
     */
    public VertxAsyncHttpClient(Vertx vertx, WebClientOptions options) {
        Objects.requireNonNull(vertx, "vertx cannot be null");
        Objects.requireNonNull(options, "options cannot be null");
        this.vertx = vertx;
        this.webClientOptions = options;
        this.client = WebClient.create(vertx, options);
    }

    @Override
    public Mono<HttpResponse> send(HttpRequest request) {
        return send(request, Context.NONE);
    }

    @Override
    public Mono<HttpResponse> send(HttpRequest request, Context context) {
        boolean eagerlyReadResponse = (boolean) context.getData("azure-eagerly-read-response").orElse(false);
        return Mono.create(sink -> sink.onRequest(value -> {
            toVertxHttpRequest(request).subscribe(vertxHttpRequest -> {
                vertxHttpRequest.send(new VertxHttpResponseHandler(request, sink, eagerlyReadResponse));
            }, sink::error);
        }));
    }

    /**
     * Closes the {@link VertxAsyncHttpClient}.
     */
    public void close() {
        if (this.client != null) {
            this.client.close();
        }

        if (isManagedVertx() && this.vertx != null) {
            CountDownLatch latch = new CountDownLatch(1);
            this.vertx.close(event -> {
                latch.countDown();
                if (event.failed() && event.cause() != null) {
                    LOGGER.logThrowableAsError(event.cause());
                }
            });
            try {
                if (!latch.await(10, TimeUnit.SECONDS)) {
                    LOGGER.warning("Timeout closing Vertx");
                }
            } catch (InterruptedException e) {
                LOGGER.logThrowableAsError(e);
            }
        }
    }

    boolean isManagedVertx() {
        return managedVertx;
    }

    void setManagedVertx(boolean managedVertx) {
        this.managedVertx = managedVertx;
    }

    private Mono<VertxHttpRequest> toVertxHttpRequest(HttpRequest request) {
        return Mono.from(convertBodyToBuffer(request))
                .map(buffer -> {
                    HttpMethod httpMethod = request.getHttpMethod();
                    io.vertx.core.http.HttpMethod requestMethod = io.vertx.core.http.HttpMethod.valueOf(httpMethod.name());

                    URL url = request.getUrl();
                    if (url.getPath().isEmpty()) {
                        try {
                            url = new URL(url.getProtocol(), url.getHost(), url.getPort(), "/" + url.getFile());
                        } catch (MalformedURLException e) {
                            throw LOGGER.logExceptionAsError(new RuntimeException(e));
                        }
                    }

                    io.vertx.ext.web.client.HttpRequest<Buffer> delegate = client
                            .requestAbs(requestMethod, url.toString());

                    if (request.getHeaders() != null) {
                        request.getHeaders()
                                .stream()
                                .forEach(httpHeader -> delegate.putHeader(httpHeader.getName(),
                                        httpHeader.getValuesList()));
                    }

                    return new VertxHttpRequest(delegate, buffer);
                });
    }

    private Mono<Buffer> convertBodyToBuffer(HttpRequest request) {
        return Mono.using(() -> Buffer.buffer(),
                buffer -> getBody(request).reduce(buffer, (b, byteBuffer) -> {
                    for (int i = 0; i < byteBuffer.limit(); i++) {
                        b.appendByte(byteBuffer.get(i));
                    }
                    return b;
                }), buffer -> buffer.getClass());
    }

    private Flux<ByteBuffer> getBody(HttpRequest request) {
        long contentLength = 0;
        String contentLengthHeader = request.getHeaders().getValue("content-length");
        if (contentLengthHeader != null) {
            contentLength = Long.parseLong(contentLengthHeader);
        }

        Flux<ByteBuffer> body = request.getBody();
        if (body == null || contentLength <= 0) {
            body = Flux.just(Buffer.buffer().getByteBuf().nioBuffer());
        }

        return body;
    }
}
