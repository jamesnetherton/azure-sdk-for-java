// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mediaservices;

import com.azure.core.util.Context;

/** Samples for LiveEvents Allocate. */
public final class LiveEventsAllocateSamples {
    /**
     * Sample code: Allocate a LiveEvent.
     *
     * @param mediaServicesManager Entry point to MediaServicesManager. This Swagger was generated by the API Framework.
     */
    public static void allocateALiveEvent(
        com.azure.resourcemanager.mediaservices.MediaServicesManager mediaServicesManager) {
        mediaServicesManager.liveEvents().allocate("mediaresources", "slitestmedia10", "myLiveEvent1", Context.NONE);
    }
}