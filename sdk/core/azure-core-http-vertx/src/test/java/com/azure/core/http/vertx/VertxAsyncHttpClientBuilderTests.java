// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.ProxyOptions;
import com.azure.core.test.utils.TestConfigurationSource;
import com.azure.core.util.Configuration;
import com.azure.core.util.ConfigurationBuilder;
import com.azure.core.util.ConfigurationSource;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
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
    private static final String PROXY_USERNAME = "foo";
    private static final String PROXY_PASSWORD = "bar";
    private static final String PROXY_USER_INFO = PROXY_USERNAME + ":" + PROXY_PASSWORD + "@";
    private static final String SERVICE_ENDPOINT = "/default";
    private static final ConfigurationSource EMPTY_SOURCE = new TestConfigurationSource();

    @Test
    public void buildWithConfigurationNone() {
        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .configuration(Configuration.NONE)
            .build();

        String defaultPath = "/default";
        WireMockServer server
            = new WireMockServer(WireMockConfiguration.options().dynamicPort().disableRequestJournal());
        server.stubFor(WireMock.get(defaultPath).willReturn(WireMock.aResponse().withStatus(200)));
        server.start();
        String defaultUrl = "http://localhost:" + server.port() + defaultPath;
        try {
            StepVerifier.create(client.send(new HttpRequest(HttpMethod.GET, defaultUrl)))
                .assertNext(response -> assertEquals(200, response.getStatusCode()))
                .verifyComplete();
        } finally {
            client.close();
            if (server.isRunning()) {
                server.shutdown();
            }
        }
    }

    @Test
    public void buildWithDefaultConnectionOptions() {
        VertxAsyncHttpClient client = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
            .build();

        WebClientOptions options = client.webClientOptions;

        String defaultPath = "/default";
        WireMockServer server
            = new WireMockServer(WireMockConfiguration.options().dynamicPort().disableRequestJournal());
        server.stubFor(WireMock.get(defaultPath).willReturn(WireMock.aResponse().withStatus(200)));
        server.start();
        String defaultUrl = "http://localhost:" + server.port() + defaultPath;
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
            if (server.isRunning()) {
                server.shutdown();
            }
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

        String defaultPath = "/default";
        WireMockServer server
            = new WireMockServer(WireMockConfiguration.options().dynamicPort().disableRequestJournal());
        server.stubFor(WireMock.get(defaultPath).willReturn(WireMock.aResponse().withStatus(200)));
        server.start();
        String defaultUrl = "http://localhost:" + server.port() + defaultPath;
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

            if (server.isRunning()) {
                server.shutdown();
            }
        }
    }

    @ParameterizedTest
    @EnumSource(ProxyOptions.Type.class)
    public void buildWithAllProxyTypes(ProxyOptions.Type type) throws Exception {
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
    public void buildWithHttpProxy() {
        SimpleBasicAuthHttpProxyServer proxyServer = new SimpleBasicAuthHttpProxyServer(PROXY_USERNAME,
            PROXY_PASSWORD,
            new String[] {SERVICE_ENDPOINT});

        VertxAsyncHttpClient httpClient = null;
        try {
            SimpleBasicAuthHttpProxyServer.ProxyEndpoint proxyEndpoint = proxyServer.start();

            ProxyOptions clientProxyOptions = new ProxyOptions(ProxyOptions.Type.HTTP,
                new InetSocketAddress(proxyEndpoint.getHost(), proxyEndpoint.getPort()))
                .setCredentials(PROXY_USERNAME, PROXY_PASSWORD);

            httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
                .proxy(clientProxyOptions)
                .build();

            final String serviceUrl = "http://localhost:80" + SERVICE_ENDPOINT;
            StepVerifier.create(httpClient.send(new HttpRequest(HttpMethod.GET, serviceUrl)))
                .expectNextCount(1)
                .verifyComplete();
        } finally {
            proxyServer.shutdown();

            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    @Test
    public void buildWithHttpProxyFromEnvConfiguration() {
        SimpleBasicAuthHttpProxyServer proxyServer = new SimpleBasicAuthHttpProxyServer(PROXY_USERNAME,
            PROXY_PASSWORD,
            new String[] {SERVICE_ENDPOINT});

        VertxAsyncHttpClient httpClient = null;
        try {
            SimpleBasicAuthHttpProxyServer.ProxyEndpoint proxyEndpoint = proxyServer.start();

            Configuration configuration = new ConfigurationBuilder(EMPTY_SOURCE, EMPTY_SOURCE,
                new TestConfigurationSource()
                    .put(Configuration.PROPERTY_HTTP_PROXY, "http://" + PROXY_USER_INFO + proxyEndpoint.getHost() + ":" + proxyEndpoint.getPort())
                    .put("java.net.useSystemProxies", "true"))
                .build();

            httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
                .configuration(configuration)
                .build();

            final String serviceUrl = "http://localhost:80" + SERVICE_ENDPOINT;
            StepVerifier.create(httpClient.send(new HttpRequest(HttpMethod.GET, serviceUrl)))
                .expectNextCount(1)
                .verifyComplete();
        } finally {
            proxyServer.shutdown();

            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    @Test
    public void buildWithHttpProxyFromExplicitConfiguration() {
        SimpleBasicAuthHttpProxyServer proxyServer = new SimpleBasicAuthHttpProxyServer(PROXY_USERNAME,
            PROXY_PASSWORD,
            new String[] {SERVICE_ENDPOINT});

        VertxAsyncHttpClient httpClient = null;
        try {
            SimpleBasicAuthHttpProxyServer.ProxyEndpoint proxyEndpoint = proxyServer.start();

            Configuration configuration = new ConfigurationBuilder()
                .putProperty("http.proxy.hostname", proxyEndpoint.getHost())
                .putProperty("http.proxy.port", String.valueOf(proxyEndpoint.getPort()))
                .build();

            httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientBuilder()
                .configuration(configuration)
                .build();

            final String serviceUrl = "http://localhost:80" + SERVICE_ENDPOINT;
            StepVerifier.create(httpClient.send(new HttpRequest(HttpMethod.GET, serviceUrl)))
                .expectNextCount(1)
                .verifyComplete();
        } finally {
            proxyServer.shutdown();

            if (httpClient != null) {
                httpClient.close();
            }
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

        String defaultPath = "/default";
        WireMockServer server
            = new WireMockServer(WireMockConfiguration.options().dynamicPort().disableRequestJournal());
        server.stubFor(WireMock.get(defaultPath).willReturn(WireMock.aResponse().withStatus(200)));
        server.start();
        String defaultUrl = "http://localhost:" + server.port() + defaultPath;
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

            if (server.isRunning()) {
                server.shutdown();
            }
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
