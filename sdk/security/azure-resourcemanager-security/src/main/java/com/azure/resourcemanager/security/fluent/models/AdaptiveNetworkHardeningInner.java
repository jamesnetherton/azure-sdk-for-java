// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.security.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.ProxyResource;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.security.models.EffectiveNetworkSecurityGroups;
import com.azure.resourcemanager.security.models.Rule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

/** The resource whose properties describes the Adaptive Network Hardening settings for some Azure resource. */
@JsonFlatten
@Fluent
public class AdaptiveNetworkHardeningInner extends ProxyResource {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(AdaptiveNetworkHardeningInner.class);

    /*
     * The security rules which are recommended to be effective on the VM
     */
    @JsonProperty(value = "properties.rules")
    private List<Rule> rules;

    /*
     * The UTC time on which the rules were calculated
     */
    @JsonProperty(value = "properties.rulesCalculationTime")
    private OffsetDateTime rulesCalculationTime;

    /*
     * The Network Security Groups effective on the network interfaces of the
     * protected resource
     */
    @JsonProperty(value = "properties.effectiveNetworkSecurityGroups")
    private List<EffectiveNetworkSecurityGroups> effectiveNetworkSecurityGroups;

    /**
     * Get the rules property: The security rules which are recommended to be effective on the VM.
     *
     * @return the rules value.
     */
    public List<Rule> rules() {
        return this.rules;
    }

    /**
     * Set the rules property: The security rules which are recommended to be effective on the VM.
     *
     * @param rules the rules value to set.
     * @return the AdaptiveNetworkHardeningInner object itself.
     */
    public AdaptiveNetworkHardeningInner withRules(List<Rule> rules) {
        this.rules = rules;
        return this;
    }

    /**
     * Get the rulesCalculationTime property: The UTC time on which the rules were calculated.
     *
     * @return the rulesCalculationTime value.
     */
    public OffsetDateTime rulesCalculationTime() {
        return this.rulesCalculationTime;
    }

    /**
     * Set the rulesCalculationTime property: The UTC time on which the rules were calculated.
     *
     * @param rulesCalculationTime the rulesCalculationTime value to set.
     * @return the AdaptiveNetworkHardeningInner object itself.
     */
    public AdaptiveNetworkHardeningInner withRulesCalculationTime(OffsetDateTime rulesCalculationTime) {
        this.rulesCalculationTime = rulesCalculationTime;
        return this;
    }

    /**
     * Get the effectiveNetworkSecurityGroups property: The Network Security Groups effective on the network interfaces
     * of the protected resource.
     *
     * @return the effectiveNetworkSecurityGroups value.
     */
    public List<EffectiveNetworkSecurityGroups> effectiveNetworkSecurityGroups() {
        return this.effectiveNetworkSecurityGroups;
    }

    /**
     * Set the effectiveNetworkSecurityGroups property: The Network Security Groups effective on the network interfaces
     * of the protected resource.
     *
     * @param effectiveNetworkSecurityGroups the effectiveNetworkSecurityGroups value to set.
     * @return the AdaptiveNetworkHardeningInner object itself.
     */
    public AdaptiveNetworkHardeningInner withEffectiveNetworkSecurityGroups(
        List<EffectiveNetworkSecurityGroups> effectiveNetworkSecurityGroups) {
        this.effectiveNetworkSecurityGroups = effectiveNetworkSecurityGroups;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (rules() != null) {
            rules().forEach(e -> e.validate());
        }
        if (effectiveNetworkSecurityGroups() != null) {
            effectiveNetworkSecurityGroups().forEach(e -> e.validate());
        }
    }
}