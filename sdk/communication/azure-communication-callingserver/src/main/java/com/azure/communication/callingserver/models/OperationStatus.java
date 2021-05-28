// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.communication.callingserver.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collection;

/** Defines values for OperationStatus. */
public final class OperationStatus extends ExpandableStringEnum<OperationStatus> {
    /** Static value notStarted for OperationStatus. */
    public static final OperationStatus NOT_STARTED = fromString("notStarted");

    /** Static value running for OperationStatus. */
    public static final OperationStatus RUNNING = fromString("running");

    /** Static value completed for OperationStatus. */
    public static final OperationStatus COMPLETED = fromString("completed");

    /** Static value failed for OperationStatus. */
    public static final OperationStatus FAILED = fromString("failed");

    /**
     * Creates or finds a OperationStatus from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding OperationStatus.
     */
    @JsonCreator
    public static OperationStatus fromString(String name) {
        return fromString(name, OperationStatus.class);
    }

    /** @return known OperationStatus values. */
    public static Collection<OperationStatus> values() {
        return values(OperationStatus.class);
    }
}