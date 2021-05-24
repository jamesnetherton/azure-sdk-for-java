// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.resourcegraph.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/** Defines values for ResourcesHistoryRequestOptionsResultFormat. */
public final class ResourcesHistoryRequestOptionsResultFormat
    extends ExpandableStringEnum<ResourcesHistoryRequestOptionsResultFormat> {
    /** Static value table for ResourcesHistoryRequestOptionsResultFormat. */
    public static final ResourcesHistoryRequestOptionsResultFormat TABLE = fromString("table");

    /** Static value objectArray for ResourcesHistoryRequestOptionsResultFormat. */
    public static final ResourcesHistoryRequestOptionsResultFormat OBJECT_ARRAY = fromString("objectArray");

    /**
     * Creates or finds a ResourcesHistoryRequestOptionsResultFormat from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding ResourcesHistoryRequestOptionsResultFormat.
     */
    @JsonCreator
    public static ResourcesHistoryRequestOptionsResultFormat fromString(String name) {
        return fromString(name, ResourcesHistoryRequestOptionsResultFormat.class);
    }

    /** @return known ResourcesHistoryRequestOptionsResultFormat values. */
    public static Collection<ResourcesHistoryRequestOptionsResultFormat> values() {
        return values(ResourcesHistoryRequestOptionsResultFormat.class);
    }
}