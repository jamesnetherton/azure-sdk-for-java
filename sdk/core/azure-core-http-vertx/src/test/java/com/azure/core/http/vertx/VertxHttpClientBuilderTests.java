// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.ProxyOptions;
import com.azure.core.util.Configuration;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import reactor.test.StepVerifier;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link VertxHttpClientBuilder}.
 */
public class VertxHttpClientBuilderTests {
    private static final String COOKIE_VALIDATOR_PATH = "/cookieValidator";
    private static final String DEFAULT_PATH = "/default";
    private static final String DISPATCHER_PATH = "/dispatcher";
    private static WireMockServer server;
    private static Vertx vertx;
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

        vertx = Vertx.vertx();
    }

    @AfterAll
    public static void afterAll() throws Exception {
        if (server.isRunning()) {
            server.shutdown();
        }

        if (vertx != null) {
            CountDownLatch latch = new CountDownLatch(1);
            vertx.close(x -> latch.countDown());
            latch.await();
        }
    }

    @Test
    public void buildWithConfigurationNone() {
        HttpClient client = new VertxHttpClientBuilder()
            .configuration(Configuration.NONE)
            .vertx(vertx)
            .build();
        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();
        } finally {
            ((VertxHttpClient) client).close();
        }
    }

    @Test
    public void buildWithDefaultConnectionOptions() {
        WebClientOptions options = new WebClientOptions();

        HttpClient client = new VertxHttpClientBuilder()
            .vertx(vertx)
            .webClientOptions(options)
            .build();

        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();

            assertEquals(options.getConnectTimeout(), 10000);
            assertEquals(options.getIdleTimeout(), 60);
            assertEquals(options.getReadIdleTimeout(), 60);
            assertEquals(options.getWriteIdleTimeout(), 60);
        } finally {
            ((VertxHttpClient) client).close();
        }
    }

    @Test
    public void buildWithConnectionOptions() {
        WebClientOptions options = new WebClientOptions();

        HttpClient client = new VertxHttpClientBuilder()
            .vertx(vertx)
            .webClientOptions(options)
            .connectTimeout(Duration.ofSeconds(10))
            .idleTimeout(Duration.ofSeconds(20))
            .readIdleTimeout(Duration.ofSeconds(30))
            .writeIdleTimeout(Duration.ofSeconds(40))
            .build();

        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();

            assertEquals(options.getConnectTimeout(), 10000);
            assertEquals(options.getIdleTimeout(), 20);
            assertEquals(options.getReadIdleTimeout(), 30);
            assertEquals(options.getWriteIdleTimeout(), 40);
        } finally {
            ((VertxHttpClient) client).close();
        }
    }

    @ParameterizedTest
    @EnumSource(ProxyOptions.Type.class)
    public void allProxyOptions(ProxyOptions.Type type) {
        String proxyUser = "user";
        String proxyPassword = "secret";

        WebClientOptions options = new WebClientOptions();
        InetSocketAddress address = new InetSocketAddress("localhost", 8888);
        ProxyOptions proxyOptions = new ProxyOptions(type, address);
        proxyOptions.setCredentials("user", "secret");
        proxyOptions.setNonProxyHosts("foo.*|*bar.com|microsoft.com");

        HttpClient client = new VertxHttpClientBuilder()
            .vertx(vertx)
            .webClientOptions(options)
            .proxy(proxyOptions)
            .build();

        try {
            io.vertx.core.net.ProxyOptions vertxProxyOptions = options.getProxyOptions();
            assertEquals(vertxProxyOptions.getHost(), address.getHostName());
            assertEquals(vertxProxyOptions.getPort(), address.getPort());
            assertEquals(vertxProxyOptions.getType().name(), type.name());
            assertEquals(vertxProxyOptions.getUsername(), proxyUser);
            assertEquals(vertxProxyOptions.getPassword(), proxyPassword);

            List<String> proxyHosts = new ArrayList<>();
            proxyHosts.add("foo*");
            proxyHosts.add("*bar.com");
            proxyHosts.add("microsoft.com");
            assertEquals(proxyHosts, options.getNonProxyHosts());
        } finally {
            ((VertxHttpClient) client).close();
        }
    }
}
