/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for ResultType.
 */
public enum ResultType {
    /** Enum value Data. */
    DATA("Data"),

    /** Enum value Metadata. */
    METADATA("Metadata");

    /** The actual serialized value for a ResultType instance. */
    private String value;

    ResultType(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a ResultType instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed ResultType object, or null if unable to parse.
     */
    @JsonCreator
    public static ResultType fromString(String value) {
        ResultType[] items = ResultType.values();
        for (ResultType item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
