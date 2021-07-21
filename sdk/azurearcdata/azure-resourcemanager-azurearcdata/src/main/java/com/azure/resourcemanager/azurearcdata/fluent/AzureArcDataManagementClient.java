// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.azurearcdata.fluent;

import com.azure.core.http.HttpPipeline;
import java.time.Duration;

/** The interface for AzureArcDataManagementClient class. */
public interface AzureArcDataManagementClient {
    /**
     * Gets The ID of the Azure subscription.
     *
     * @return the subscriptionId value.
     */
    String getSubscriptionId();

    /**
     * Gets server parameter.
     *
     * @return the endpoint value.
     */
    String getEndpoint();

    /**
     * Gets Api Version.
     *
     * @return the apiVersion value.
     */
    String getApiVersion();

    /**
     * Gets The HTTP pipeline to send requests through.
     *
     * @return the httpPipeline value.
     */
    HttpPipeline getHttpPipeline();

    /**
     * Gets The default poll interval for long-running operation.
     *
     * @return the defaultPollInterval value.
     */
    Duration getDefaultPollInterval();

    /**
     * Gets the OperationsClient object to access its operations.
     *
     * @return the OperationsClient object.
     */
    OperationsClient getOperations();

    /**
     * Gets the SqlManagedInstancesClient object to access its operations.
     *
     * @return the SqlManagedInstancesClient object.
     */
    SqlManagedInstancesClient getSqlManagedInstances();

    /**
     * Gets the SqlServerInstancesClient object to access its operations.
     *
     * @return the SqlServerInstancesClient object.
     */
    SqlServerInstancesClient getSqlServerInstances();

    /**
     * Gets the DataControllersClient object to access its operations.
     *
     * @return the DataControllersClient object.
     */
    DataControllersClient getDataControllers();
}