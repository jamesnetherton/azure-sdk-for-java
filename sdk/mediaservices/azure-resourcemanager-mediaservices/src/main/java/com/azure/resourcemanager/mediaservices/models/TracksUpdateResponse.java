// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mediaservices.models;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.ResponseBase;
import com.azure.resourcemanager.mediaservices.fluent.models.AssetTrackInner;

/** Contains all response data for the update operation. */
public final class TracksUpdateResponse extends ResponseBase<TracksUpdateHeaders, AssetTrackInner> {
    /**
     * Creates an instance of TracksUpdateResponse.
     *
     * @param request the request which resulted in this TracksUpdateResponse.
     * @param statusCode the status code of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param value the deserialized value of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     */
    public TracksUpdateResponse(
        HttpRequest request,
        int statusCode,
        HttpHeaders rawHeaders,
        AssetTrackInner value,
        TracksUpdateHeaders headers) {
        super(request, statusCode, rawHeaders, value, headers);
    }

    /** @return the deserialized response body. */
    @Override
    public AssetTrackInner getValue() {
        return super.getValue();
    }
}
