// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.security.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.security.fluent.RegulatoryComplianceControlsClient;
import com.azure.resourcemanager.security.fluent.models.RegulatoryComplianceControlInner;
import com.azure.resourcemanager.security.models.RegulatoryComplianceControl;
import com.azure.resourcemanager.security.models.RegulatoryComplianceControls;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class RegulatoryComplianceControlsImpl implements RegulatoryComplianceControls {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(RegulatoryComplianceControlsImpl.class);

    private final RegulatoryComplianceControlsClient innerClient;

    private final com.azure.resourcemanager.security.SecurityManager serviceManager;

    public RegulatoryComplianceControlsImpl(
        RegulatoryComplianceControlsClient innerClient,
        com.azure.resourcemanager.security.SecurityManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public PagedIterable<RegulatoryComplianceControl> list(String regulatoryComplianceStandardName) {
        PagedIterable<RegulatoryComplianceControlInner> inner =
            this.serviceClient().list(regulatoryComplianceStandardName);
        return Utils.mapPage(inner, inner1 -> new RegulatoryComplianceControlImpl(inner1, this.manager()));
    }

    public PagedIterable<RegulatoryComplianceControl> list(
        String regulatoryComplianceStandardName, String filter, Context context) {
        PagedIterable<RegulatoryComplianceControlInner> inner =
            this.serviceClient().list(regulatoryComplianceStandardName, filter, context);
        return Utils.mapPage(inner, inner1 -> new RegulatoryComplianceControlImpl(inner1, this.manager()));
    }

    public RegulatoryComplianceControl get(
        String regulatoryComplianceStandardName, String regulatoryComplianceControlName) {
        RegulatoryComplianceControlInner inner =
            this.serviceClient().get(regulatoryComplianceStandardName, regulatoryComplianceControlName);
        if (inner != null) {
            return new RegulatoryComplianceControlImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<RegulatoryComplianceControl> getWithResponse(
        String regulatoryComplianceStandardName, String regulatoryComplianceControlName, Context context) {
        Response<RegulatoryComplianceControlInner> inner =
            this
                .serviceClient()
                .getWithResponse(regulatoryComplianceStandardName, regulatoryComplianceControlName, context);
        if (inner != null) {
            return new SimpleResponse<>(
                inner.getRequest(),
                inner.getStatusCode(),
                inner.getHeaders(),
                new RegulatoryComplianceControlImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    private RegulatoryComplianceControlsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.resourcemanager.security.SecurityManager manager() {
        return this.serviceManager;
    }
}