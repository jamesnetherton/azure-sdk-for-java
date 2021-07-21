// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.azurearcdata.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.management.polling.PollResult;
import com.azure.core.util.Context;
import com.azure.core.util.polling.SyncPoller;
import com.azure.resourcemanager.azurearcdata.fluent.models.DataControllerResourceInner;
import com.azure.resourcemanager.azurearcdata.models.DataControllerUpdate;

/** An instance of this class provides access to all the operations defined in DataControllersClient. */
public interface DataControllersClient {
    /**
     * List dataController resources in the subscription.
     *
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<DataControllerResourceInner> list();

    /**
     * List dataController resources in the subscription.
     *
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<DataControllerResourceInner> list(Context context);

    /**
     * List dataController resources in the resource group.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<DataControllerResourceInner> listByResourceGroup(String resourceGroupName);

    /**
     * List dataController resources in the resource group.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<DataControllerResourceInner> listByResourceGroup(String resourceGroupName, Context context);

    /**
     * Creates or replaces a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource desc.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    SyncPoller<PollResult<DataControllerResourceInner>, DataControllerResourceInner> beginPutDataController(
        String resourceGroupName, String dataControllerName, DataControllerResourceInner dataControllerResource);

    /**
     * Creates or replaces a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource desc.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    SyncPoller<PollResult<DataControllerResourceInner>, DataControllerResourceInner> beginPutDataController(
        String resourceGroupName,
        String dataControllerName,
        DataControllerResourceInner dataControllerResource,
        Context context);

    /**
     * Creates or replaces a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource desc.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    DataControllerResourceInner putDataController(
        String resourceGroupName, String dataControllerName, DataControllerResourceInner dataControllerResource);

    /**
     * Creates or replaces a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource desc.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    DataControllerResourceInner putDataController(
        String resourceGroupName,
        String dataControllerName,
        DataControllerResourceInner dataControllerResource,
        Context context);

    /**
     * Deletes a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String dataControllerName);

    /**
     * Deletes a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<Void> deleteWithResponse(String resourceGroupName, String dataControllerName, Context context);

    /**
     * Retrieves a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    DataControllerResourceInner getByResourceGroup(String resourceGroupName, String dataControllerName);

    /**
     * Retrieves a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<DataControllerResourceInner> getByResourceGroupWithResponse(
        String resourceGroupName, String dataControllerName, Context context);

    /**
     * Updates a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource The update data controller resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    DataControllerResourceInner patchDataController(
        String resourceGroupName, String dataControllerName, DataControllerUpdate dataControllerResource);

    /**
     * Updates a dataController resource.
     *
     * @param resourceGroupName The name of the Azure resource group.
     * @param dataControllerName The dataControllerName parameter.
     * @param dataControllerResource The update data controller resource.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return data controller resource.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<DataControllerResourceInner> patchDataControllerWithResponse(
        String resourceGroupName,
        String dataControllerName,
        DataControllerUpdate dataControllerResource,
        Context context);
}