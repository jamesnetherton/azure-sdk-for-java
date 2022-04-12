// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.http.ProxyOptions;
import com.azure.core.test.RestProxyTestsWireMockServer;
import com.azure.core.test.implementation.RestProxyTests;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

@Disabled("Should only be run manually when a local proxy server (e.g. Fiddler) is running")
public class VertxHttpClientRestProxyWithAsyncHttpProxyTests extends RestProxyTests {
    private static WireMockServer server;
    private static Vertx vertx;

    @BeforeAll
    public static void beforeAll() {
        server = RestProxyTestsWireMockServer.getRestProxyTestsServer();
        server.start();
        vertx = Vertx.vertx();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        if (server != null) {
            server.shutdown();
        }

        if (vertx != null) {
            CountDownLatch latch = new CountDownLatch(1);
            vertx.close(x -> latch.countDown());
            latch.await();
        }
    }

    @Override
    protected int getWireMockPort() {
        return server.port();
    }

    @Override
    protected HttpClient createHttpClient() {
        ProxyOptions proxyOptions = new ProxyOptions(ProxyOptions.Type.HTTP,
                new InetSocketAddress("localhost", 8888));
        return new VertxAsyncHttpClientBuilder()
                .vertx(vertx)
                .proxy(proxyOptions)
                .build();
    }
}
