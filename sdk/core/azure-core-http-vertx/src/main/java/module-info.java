// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

module com.azure.core.http.vertx {
    requires transitive com.azure.core;

    requires io.netty.buffer;
    requires io.vertx.core;
    requires io.vertx.web.client;

    exports com.azure.core.http.vertx;

    provides com.azure.core.http.HttpClientProvider
        with com.azure.core.http.vertx.VertxHttpClientProvider;

    uses com.azure.core.http.HttpClientProvider;
    uses com.azure.core.http.vertx.VertxProvider;
}
