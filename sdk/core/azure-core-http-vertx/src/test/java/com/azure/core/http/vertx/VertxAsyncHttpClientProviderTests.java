// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.http.ProxyOptions;
import com.azure.core.util.Configuration;
import com.azure.core.util.HttpClientOptions;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.impl.WebClientBase;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests {@link VertxAsyncHttpClientProvider}.
 */
public class VertxAsyncHttpClientProviderTests {

    @Test
    public void nullOptionsReturnsBaseClient() throws Exception {
        VertxAsyncHttpClient httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientProvider()
            .createInstance(null);

        ProxyOptions environmentProxy = ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration());
        WebClientOptions options = getWebClientOptions(httpClient.client);
        io.vertx.core.net.ProxyOptions proxyOptions = options.getProxyOptions();
        if (environmentProxy == null) {
            assertNull(proxyOptions);
        } else {
            assertNotNull(proxyOptions);
            assertEquals(environmentProxy.getAddress().getHostName(), proxyOptions.getHost());
        }
    }

    @Test
    public void defaultOptionsReturnsBaseClient() throws Exception {
        VertxAsyncHttpClient httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientProvider()
            .createInstance(new HttpClientOptions());

        ProxyOptions environmentProxy = ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration());
        WebClientOptions options = getWebClientOptions(httpClient.client);
        io.vertx.core.net.ProxyOptions proxyOptions = options.getProxyOptions();
        if (environmentProxy == null) {
            assertNull(proxyOptions);
        } else {
            assertNotNull(proxyOptions);
            assertEquals(environmentProxy.getAddress().getHostName(), proxyOptions.getHost());
        }
    }

    @Test
    public void optionsWithAProxy() throws Exception {
        ProxyOptions proxyOptions = new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("localhost", 8888));
        proxyOptions.setNonProxyHosts("foo.*|bar.*|cheese.com|wine.org");

        HttpClientOptions clientOptions = new HttpClientOptions().setProxyOptions(proxyOptions);

        VertxAsyncHttpClient httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientProvider()
            .createInstance(clientOptions);

        WebClientOptions options = getWebClientOptions(httpClient.client);
        io.vertx.core.net.ProxyOptions vertxProxyOptions = options.getProxyOptions();
        assertNotNull(vertxProxyOptions);
        assertEquals(proxyOptions.getAddress().getHostName(), vertxProxyOptions.getHost());
        assertEquals(proxyOptions.getAddress().getPort(), vertxProxyOptions.getPort());
        assertEquals(proxyOptions.getType().name(), vertxProxyOptions.getType().name());
    }

    @Test
    public void optionsWithTimeouts() throws Exception {
        Duration timeout = Duration.ofMillis(15000);
        HttpClientOptions clientOptions = new HttpClientOptions()
            .setConnectTimeout(timeout)
            .setConnectionIdleTimeout(timeout)
            .setReadTimeout(timeout)
            .setWriteTimeout(timeout);

        VertxAsyncHttpClient httpClient = (VertxAsyncHttpClient) new VertxAsyncHttpClientProvider()
            .createInstance(clientOptions);

        WebClientOptions options = getWebClientOptions(httpClient.client);

        assertEquals(timeout.toMillis(), options.getConnectTimeout());
        assertEquals(timeout.getSeconds(), options.getIdleTimeout());
        assertEquals(timeout.getSeconds(), options.getReadIdleTimeout());
        assertEquals(timeout.getSeconds(), options.getWriteIdleTimeout());
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void vertxProviderServiceLoader() throws Exception {
        Vertx vertx = Vertx.vertx();

        ServiceLoader mockServiceLoader = mock(ServiceLoader.class);
        VertxProvider mockVertxProvider = mock(VertxProvider.class);

        try (MockedStatic<ServiceLoader> serviceLoader = mockStatic(ServiceLoader.class)) {
            Set<VertxProvider> providers = new HashSet<>();
            providers.add(mockVertxProvider);

            Class<?> providerClass = VertxProvider.class;
            serviceLoader.when(() -> ServiceLoader.load(providerClass, providerClass.getClassLoader()))
                .thenReturn(mockServiceLoader);

            Mockito.when(mockServiceLoader.iterator()).thenReturn(providers.iterator());
            Mockito.when(mockVertxProvider.createVertx()).thenReturn(vertx);

            HttpClient httpClient = new VertxAsyncHttpClientProvider().createInstance();
            assertNotNull(httpClient);

            verify(mockServiceLoader, times(1)).iterator();
            verify(mockVertxProvider, times(1)).createVertx();
        } finally {
            CountDownLatch latch = new CountDownLatch(1);
            vertx.close(event -> latch.countDown());
            latch.await(5, TimeUnit.SECONDS);
        }
    }

    private WebClientOptions getWebClientOptions(WebClient client) throws Exception {
        // Use reflection to get the configured WebClientOptions from a configured WebClient.
        // It is not exposed by default and this avoids having to implement workarounds
        // in the client code to make it available just for testing purposes.
        Field field = WebClientBase.class.getDeclaredField("options");
        field.setAccessible(true);
        return (WebClientOptions) field.get(client);
    }
}
