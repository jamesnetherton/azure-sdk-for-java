// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.vertx;

import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpClientProvider;
import com.azure.core.util.HttpClientOptions;
import io.vertx.ext.web.client.WebClient;

/**
 * {@link HttpClientProvider} backed by the Vert.x {@link WebClient}
 */
public class VertxAsyncHttpClientProvider implements HttpClientProvider {

    @Override
    public HttpClient createInstance() {
        return createInstance(null);
    }

    @Override
    public HttpClient createInstance(HttpClientOptions clientOptions) {
        VertxAsyncHttpClientBuilder builder = new VertxAsyncHttpClientBuilder();
        if (clientOptions != null) {
            builder = builder.proxy(clientOptions.getProxyOptions())
                    .configuration(clientOptions.getConfiguration())
                    .connectTimeout(clientOptions.getConnectTimeout())
                    .idleTimeout(clientOptions.getConnectionIdleTimeout())
                    .writeIdleTimeout(clientOptions.getWriteTimeout())
                    .readIdleTimeout(clientOptions.getReadTimeout());
        }
        return builder.build();
    }
}
