// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.communication.callingserver.implementation.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/** Defines values for CallRecordingStateModel. */
public final class CallRecordingStateModel extends ExpandableStringEnum<CallRecordingStateModel> {
    /** Static value active for CallRecordingStateModel. */
    public static final CallRecordingStateModel ACTIVE = fromString("active");

    /** Static value inactive for CallRecordingStateModel. */
    public static final CallRecordingStateModel INACTIVE = fromString("inactive");

    /**
     * Creates or finds a CallRecordingStateModel from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding CallRecordingStateModel.
     */
    @JsonCreator
    public static CallRecordingStateModel fromString(String name) {
        return fromString(name, CallRecordingStateModel.class);
    }

    /** @return known CallRecordingStateModel values. */
    public static Collection<CallRecordingStateModel> values() {
        return values(CallRecordingStateModel.class);
    }
}