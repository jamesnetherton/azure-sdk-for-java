// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mediaservices;

import com.azure.resourcemanager.mediaservices.models.ContentKeyPolicyConfiguration;
import com.azure.resourcemanager.mediaservices.models.ContentKeyPolicyOption;
import com.azure.resourcemanager.mediaservices.models.ContentKeyPolicyRestriction;
import java.util.Arrays;

/** Samples for ContentKeyPolicies CreateOrUpdate. */
public final class ContentKeyPoliciesCreateOrUpdateSamples {
    /**
     * Sample code: Creates a Content Key Policy with multiple options.
     *
     * @param mediaServicesManager Entry point to MediaServicesManager. This Swagger was generated by the API Framework.
     */
    public static void createsAContentKeyPolicyWithMultipleOptions(
        com.azure.resourcemanager.mediaservices.MediaServicesManager mediaServicesManager) {
        mediaServicesManager
            .contentKeyPolicies()
            .define("PolicyCreatedWithMultipleOptions")
            .withExistingMediaService("contoso", "contosomedia")
            .withDescription("ArmPolicyDescription")
            .withOptions(
                Arrays
                    .asList(
                        new ContentKeyPolicyOption()
                            .withName("ClearKeyOption")
                            .withConfiguration(new ContentKeyPolicyConfiguration())
                            .withRestriction(new ContentKeyPolicyRestriction()),
                        new ContentKeyPolicyOption()
                            .withName("widevineoption")
                            .withConfiguration(new ContentKeyPolicyConfiguration())
                            .withRestriction(new ContentKeyPolicyRestriction())))
            .create();
    }

    /**
     * Sample code: Creates a Content Key Policy with ClearKey option and Token Restriction.
     *
     * @param mediaServicesManager Entry point to MediaServicesManager. This Swagger was generated by the API Framework.
     */
    public static void createsAContentKeyPolicyWithClearKeyOptionAndTokenRestriction(
        com.azure.resourcemanager.mediaservices.MediaServicesManager mediaServicesManager) {
        mediaServicesManager
            .contentKeyPolicies()
            .define("PolicyWithClearKeyOptionAndSwtTokenRestriction")
            .withExistingMediaService("contoso", "contosomedia")
            .withDescription("ArmPolicyDescription")
            .withOptions(
                Arrays
                    .asList(
                        new ContentKeyPolicyOption()
                            .withName("ClearKeyOption")
                            .withConfiguration(new ContentKeyPolicyConfiguration())
                            .withRestriction(new ContentKeyPolicyRestriction())))
            .create();
    }

    /**
     * Sample code: Creates a Content Key Policy with PlayReady option and Open Restriction.
     *
     * @param mediaServicesManager Entry point to MediaServicesManager. This Swagger was generated by the API Framework.
     */
    public static void createsAContentKeyPolicyWithPlayReadyOptionAndOpenRestriction(
        com.azure.resourcemanager.mediaservices.MediaServicesManager mediaServicesManager) {
        mediaServicesManager
            .contentKeyPolicies()
            .define("PolicyWithPlayReadyOptionAndOpenRestriction")
            .withExistingMediaService("contoso", "contosomedia")
            .withDescription("ArmPolicyDescription")
            .withOptions(
                Arrays
                    .asList(
                        new ContentKeyPolicyOption()
                            .withName("ArmPolicyOptionName")
                            .withConfiguration(new ContentKeyPolicyConfiguration())
                            .withRestriction(new ContentKeyPolicyRestriction())))
            .create();
    }

    /**
     * Sample code: Creates a Content Key Policy with Widevine option and Token Restriction.
     *
     * @param mediaServicesManager Entry point to MediaServicesManager. This Swagger was generated by the API Framework.
     */
    public static void createsAContentKeyPolicyWithWidevineOptionAndTokenRestriction(
        com.azure.resourcemanager.mediaservices.MediaServicesManager mediaServicesManager) {
        mediaServicesManager
            .contentKeyPolicies()
            .define("PolicyWithWidevineOptionAndJwtTokenRestriction")
            .withExistingMediaService("contoso", "contosomedia")
            .withDescription("ArmPolicyDescription")
            .withOptions(
                Arrays
                    .asList(
                        new ContentKeyPolicyOption()
                            .withName("widevineoption")
                            .withConfiguration(new ContentKeyPolicyConfiguration())
                            .withRestriction(new ContentKeyPolicyRestriction())))
            .create();
    }
}