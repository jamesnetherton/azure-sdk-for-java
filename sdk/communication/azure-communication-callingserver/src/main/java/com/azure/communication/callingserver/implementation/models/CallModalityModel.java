// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.communication.callingserver.implementation.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/** Defines values for CallModalityModel. */
public final class CallModalityModel extends ExpandableStringEnum<CallModalityModel> {
    /** Static value audio for CallModalityModel. */
    public static final CallModalityModel AUDIO = fromString("audio");

    /** Static value video for CallModalityModel. */
    public static final CallModalityModel VIDEO = fromString("video");

    /**
     * Creates or finds a CallModalityModel from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding CallModalityModel.
     */
    @JsonCreator
    public static CallModalityModel fromString(String name) {
        return fromString(name, CallModalityModel.class);
    }

    /** @return known CallModalityModel values. */
    public static Collection<CallModalityModel> values() {
        return values(CallModalityModel.class);
    }
}