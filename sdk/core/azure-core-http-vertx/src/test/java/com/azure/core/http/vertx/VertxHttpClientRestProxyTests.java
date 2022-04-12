// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.test.RestProxyTestsWireMockServer;
import com.azure.core.test.implementation.RestProxyTests;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;

public class VertxHttpClientRestProxyTests extends RestProxyTests {
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
        return new VertxHttpClientBuilder()
            .vertx(vertx)
            .build();
    }
}
