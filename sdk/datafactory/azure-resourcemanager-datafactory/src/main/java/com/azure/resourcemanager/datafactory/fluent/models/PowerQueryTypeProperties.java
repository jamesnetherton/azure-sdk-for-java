// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.datafactory.models.PowerQuerySource;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Power Query data flow type properties. */
@Fluent
public final class PowerQueryTypeProperties {
    /*
     * List of sources in Power Query.
     */
    @JsonProperty(value = "sources")
    private List<PowerQuerySource> sources;

    /*
     * Power query mashup script.
     */
    @JsonProperty(value = "script")
    private String script;

    /*
     * Locale of the Power query mashup document.
     */
    @JsonProperty(value = "documentLocale")
    private String documentLocale;

    /**
     * Get the sources property: List of sources in Power Query.
     *
     * @return the sources value.
     */
    public List<PowerQuerySource> sources() {
        return this.sources;
    }

    /**
     * Set the sources property: List of sources in Power Query.
     *
     * @param sources the sources value to set.
     * @return the PowerQueryTypeProperties object itself.
     */
    public PowerQueryTypeProperties withSources(List<PowerQuerySource> sources) {
        this.sources = sources;
        return this;
    }

    /**
     * Get the script property: Power query mashup script.
     *
     * @return the script value.
     */
    public String script() {
        return this.script;
    }

    /**
     * Set the script property: Power query mashup script.
     *
     * @param script the script value to set.
     * @return the PowerQueryTypeProperties object itself.
     */
    public PowerQueryTypeProperties withScript(String script) {
        this.script = script;
        return this;
    }

    /**
     * Get the documentLocale property: Locale of the Power query mashup document.
     *
     * @return the documentLocale value.
     */
    public String documentLocale() {
        return this.documentLocale;
    }

    /**
     * Set the documentLocale property: Locale of the Power query mashup document.
     *
     * @param documentLocale the documentLocale value to set.
     * @return the PowerQueryTypeProperties object itself.
     */
    public PowerQueryTypeProperties withDocumentLocale(String documentLocale) {
        this.documentLocale = documentLocale;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (sources() != null) {
            sources().forEach(e -> e.validate());
        }
    }
}
