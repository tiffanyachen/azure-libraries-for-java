/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.containerinstance.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The logs.
 */
public class LogsInner {
    /**
     * The content of the log.
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * Get the content value.
     *
     * @return the content value
     */
    public String content() {
        return this.content;
    }

    /**
     * Set the content value.
     *
     * @param content the content value to set
     * @return the LogsInner object itself.
     */
    public LogsInner withContent(String content) {
        this.content = content;
        return this;
    }

}