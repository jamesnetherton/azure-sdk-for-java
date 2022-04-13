// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.ProxyOptions;
import com.azure.core.util.Configuration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests {@link VertxAsyncHttpClientBuilder}.
 */
public class VertxAsyncHttpClientBuilderTests {
    private static final String COOKIE_VALIDATOR_PATH = "/cookieValidator";
    private static final String DEFAULT_PATH = "/default";
    private static final String DISPATCHER_PATH = "/dispatcher";
    private static WireMockServer server;
    private static String defaultUrl;

    @BeforeAll
    public static void beforeAll() {
        server = new WireMockServer(WireMockConfiguration.options().dynamicPort().disableRequestJournal());

        // Mocked endpoint to test building a client with a prebuilt OkHttpClient.
        server.stubFor(WireMock.get(COOKIE_VALIDATOR_PATH).withCookie("test", WireMock.matching("success"))
            .willReturn(WireMock.aResponse().withStatus(200)));

        // Mocked endpoint to test building a client with a timeout.
        server.stubFor(WireMock.get(DEFAULT_PATH).willReturn(WireMock.aResponse().withStatus(200)));

        // Mocked endpoint to test building a client with a dispatcher and uses a delayed response.
        server.stubFor(WireMock.get(DISPATCHER_PATH).willReturn(WireMock.aResponse().withStatus(200)
            .withFixedDelay(5000)));

        server.start();

        defaultUrl = "http://localhost:" + server.port() + DEFAULT_PATH;
    }

    @AfterAll
    public static void afterAll() throws Exception {
        if (server.isRunning()) {
            server.shutdown();
        }
    }

    @Test
    public void buildWithConfigurationNone() {
        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .configuration(Configuration.NONE)
            .build();
        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();
        } finally {
            client.close();
        }
    }

    @Test
    public void buildWithDefaultConnectionOptions() {
        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .build();

        WebClientOptions options = client.webClientOptions;

        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();

            assertEquals(10000, options.getConnectTimeout());
            assertEquals(60, options.getIdleTimeout());
            assertEquals(60, options.getReadIdleTimeout());
            assertEquals(60, options.getWriteIdleTimeout());
        } finally {
            client.close();
        }
    }

    @Test
    public void buildWithConnectionOptions() {
        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .idleTimeout(Duration.ofSeconds(20))
            .readIdleTimeout(Duration.ofSeconds(30))
            .writeIdleTimeout(Duration.ofSeconds(40))
            .build();

        WebClientOptions options = client.webClientOptions;

        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();

            assertEquals(10000, options.getConnectTimeout());
            assertEquals(20, options.getIdleTimeout());
            assertEquals(30, options.getReadIdleTimeout());
            assertEquals(40, options.getWriteIdleTimeout());
        } finally {
            client.close();
        }
    }

    @ParameterizedTest
    @EnumSource(ProxyOptions.Type.class)
    public void allProxyOptions(ProxyOptions.Type type) throws Exception {
        System.out.println(type.name());

        String proxyUser = "user";
        String proxyPassword = "secret";

        InetSocketAddress address = new InetSocketAddress("localhost", 8888);
        ProxyOptions proxyOptions = new ProxyOptions(type, address);
        proxyOptions.setCredentials("user", "secret");
        proxyOptions.setNonProxyHosts("foo.*|*bar.com|microsoft.com");

        VertxAsyncHttpClient httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .proxy(proxyOptions)
            .build();

        WebClientOptions options = httpClient.webClientOptions;

        try {
            io.vertx.core.net.ProxyOptions vertxProxyOptions = options.getProxyOptions();
            assertEquals(address.getHostName(), vertxProxyOptions.getHost());
            assertEquals(address.getPort(), vertxProxyOptions.getPort());
            assertEquals(type.name(), vertxProxyOptions.getType().name());
            assertEquals(proxyUser, vertxProxyOptions.getUsername());
            assertEquals(proxyPassword, vertxProxyOptions.getPassword());

            List<String> proxyHosts = new ArrayList<>();
            proxyHosts.add("foo*");
            proxyHosts.add("*bar.com");
            proxyHosts.add("microsoft.com");
            assertEquals(proxyHosts, options.getNonProxyHosts());
        } finally {
            httpClient.close();
        }
    }

    @Test
    public void customWebClientOptionsNotOverridden() {
        WebClientOptions options = new WebClientOptions();
        options.setConnectTimeout(30000);
        options.setIdleTimeout(50);
        options.setReadIdleTimeout(60);
        options.setWriteIdleTimeout(70);

        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .idleTimeout(Duration.ofSeconds(20))
            .readIdleTimeout(Duration.ofSeconds(30))
            .writeIdleTimeout(Duration.ofSeconds(40))
            .webClientOptions(options)
            .build();

        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();

            assertSame(options, client.webClientOptions);
            assertEquals(30000, options.getConnectTimeout());
            assertEquals(50, options.getIdleTimeout());
            assertEquals(60, options.getReadIdleTimeout());
            assertEquals(70, options.getWriteIdleTimeout());
        } finally {
            client.close();
        }
    }

    @Test
    public void unmanagedVertxCloseNotAttempted() {
        WebClientOptions options = new WebClientOptions();
        MockedStatic<WebClient> mockClient = Mockito.mockStatic(WebClient.class);
        Vertx mockVertx = Mockito.mock(Vertx.class);

        mockClient.when(() -> WebClient.create(mockVertx, options)).thenReturn(null);

        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .vertx(mockVertx)
            .webClientOptions(options)
            .build();

        client.close();

        Mockito.verify(mockVertx, Mockito.never()).close();
    }
}
