// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.ProxyOptions;
import com.azure.core.util.Configuration;
import com.azure.core.util.HttpClientOptions;
import io.vertx.ext.web.client.WebClientOptions;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests {@link VertxHttpClientProvider}.
 */
public class VertxHttpClientProviderTests {

    @Test
    public void nullOptionsReturnsBaseClient() {
        VertxHttpClient httpClient = (VertxHttpClient) new VertxHttpClientProvider()
            .createInstance(null);

        ProxyOptions environmentProxy = ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration());
        WebClientOptions options = httpClient.getWebClientOptions();
        io.vertx.core.net.ProxyOptions proxyOptions = options.getProxyOptions();
        if (environmentProxy == null) {
            assertNull(proxyOptions);
        } else {
            assertNotNull(proxyOptions);
            assertEquals(environmentProxy.getAddress().getHostName(), proxyOptions.getHost());
        }
    }

    @Test
    public void defaultOptionsReturnsBaseClient() {
        VertxHttpClient httpClient = (VertxHttpClient) new VertxHttpClientProvider()
            .createInstance(new HttpClientOptions());

        ProxyOptions environmentProxy = ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration());
        WebClientOptions options = httpClient.getWebClientOptions();
        io.vertx.core.net.ProxyOptions proxyOptions = options.getProxyOptions();
        if (environmentProxy == null) {
            assertNull(proxyOptions);
        } else {
            assertNotNull(proxyOptions);
            assertEquals(environmentProxy.getAddress().getHostName(), proxyOptions.getHost());
        }
    }

    @Test
    public void optionsWithAProxy() {
        ProxyOptions proxyOptions = new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("localhost", 8888));
        proxyOptions.setNonProxyHosts("foo.*|bar.*|cheese.com|wine.org");

        HttpClientOptions clientOptions = new HttpClientOptions().setProxyOptions(proxyOptions);

        VertxHttpClient httpClient = (VertxHttpClient) new VertxHttpClientProvider()
            .createInstance(clientOptions);

        WebClientOptions options = httpClient.getWebClientOptions();
        io.vertx.core.net.ProxyOptions vertxProxyOptions = options.getProxyOptions();
        assertNotNull(vertxProxyOptions);
        assertEquals(proxyOptions.getAddress().getHostName(), vertxProxyOptions.getHost());
        assertEquals(proxyOptions.getAddress().getPort(), vertxProxyOptions.getPort());
        assertEquals(proxyOptions.getType().name(), vertxProxyOptions.getType().name());
    }

    @Test
    public void optionsWithTimeouts() {
        long expectedTimeout = 15000;
        Duration timeout = Duration.ofMillis(expectedTimeout);
        HttpClientOptions clientOptions = new HttpClientOptions()
            .setWriteTimeout(timeout)
            .setResponseTimeout(timeout)
            .setReadTimeout(timeout);

        VertxHttpClient httpClient = (VertxHttpClient) new VertxHttpClientProvider()
            .createInstance(clientOptions);

        WebClientOptions options = httpClient.getWebClientOptions();

        assertEquals(timeout.getSeconds(), options.getWriteIdleTimeout());
        assertEquals(timeout.getSeconds(), options.getReadIdleTimeout());
    }
}
