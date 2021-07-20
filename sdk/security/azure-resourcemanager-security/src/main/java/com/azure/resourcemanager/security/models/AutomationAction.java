// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.security.models;

import com.azure.core.annotation.Immutable;
import com.azure.core.util.logging.ClientLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/** The action that should be triggered. */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "actionType",
    defaultImpl = AutomationAction.class)
@JsonTypeName("AutomationAction")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "LogicApp", value = AutomationActionLogicApp.class),
    @JsonSubTypes.Type(name = "EventHub", value = AutomationActionEventHub.class),
    @JsonSubTypes.Type(name = "Workspace", value = AutomationActionWorkspace.class)
})
@Immutable
public class AutomationAction {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(AutomationAction.class);

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}