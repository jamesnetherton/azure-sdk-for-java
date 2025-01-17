// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.dnsresolver.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.SubResource;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.dnsresolver.models.ProvisioningState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Represents the properties of a virtual network link. */
@Fluent
public final class VirtualNetworkLinkProperties {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(VirtualNetworkLinkProperties.class);

    /*
     * The reference to the virtual network. This cannot be changed after
     * creation.
     */
    @JsonProperty(value = "virtualNetwork")
    private SubResource virtualNetwork;

    /*
     * Metadata attached to the virtual network link.
     */
    @JsonProperty(value = "metadata")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.ALWAYS)
    private Map<String, String> metadata;

    /*
     * The current provisioning state of the virtual network link. This is a
     * read-only property and any attempt to set this value will be ignored.
     */
    @JsonProperty(value = "provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningState provisioningState;

    /**
     * Get the virtualNetwork property: The reference to the virtual network. This cannot be changed after creation.
     *
     * @return the virtualNetwork value.
     */
    public SubResource virtualNetwork() {
        return this.virtualNetwork;
    }

    /**
     * Set the virtualNetwork property: The reference to the virtual network. This cannot be changed after creation.
     *
     * @param virtualNetwork the virtualNetwork value to set.
     * @return the VirtualNetworkLinkProperties object itself.
     */
    public VirtualNetworkLinkProperties withVirtualNetwork(SubResource virtualNetwork) {
        this.virtualNetwork = virtualNetwork;
        return this;
    }

    /**
     * Get the metadata property: Metadata attached to the virtual network link.
     *
     * @return the metadata value.
     */
    public Map<String, String> metadata() {
        return this.metadata;
    }

    /**
     * Set the metadata property: Metadata attached to the virtual network link.
     *
     * @param metadata the metadata value to set.
     * @return the VirtualNetworkLinkProperties object itself.
     */
    public VirtualNetworkLinkProperties withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get the provisioningState property: The current provisioning state of the virtual network link. This is a
     * read-only property and any attempt to set this value will be ignored.
     *
     * @return the provisioningState value.
     */
    public ProvisioningState provisioningState() {
        return this.provisioningState;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
