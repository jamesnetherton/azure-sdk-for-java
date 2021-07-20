// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.avs;

import java.util.Arrays;

/** Samples for WorkloadNetworks CreateVMGroup. */
public final class WorkloadNetworksCreateVMGroupSamples {
    /**
     * Sample code: WorkloadNetworks_CreateVMGroup.
     *
     * @param avsManager Entry point to AvsManager. Azure VMware Solution API.
     */
    public static void workloadNetworksCreateVMGroup(com.azure.resourcemanager.avs.AvsManager avsManager) {
        avsManager
            .workloadNetworks()
            .defineVMGroup("vmGroup1")
            .withExistingPrivateCloud("group1", "cloud1")
            .withDisplayName("vmGroup1")
            .withMembers(Arrays.asList("564d43da-fefc-2a3b-1d92-42855622fa50"))
            .withRevision(1L)
            .create();
    }
}