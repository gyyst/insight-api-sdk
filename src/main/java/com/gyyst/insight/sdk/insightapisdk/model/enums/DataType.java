package com.gyyst.insight.sdk.insightapisdk.model.enums;

import lombok.Getter;

import java.util.List;


@Getter
public enum DataType {


    /**
     * This constant represents a number type.
     */
    NUMBER(1, Number.class),

    /**
     * This constant represents a string type.
     */
    STRING(2, String.class),

    /**
     * This constant represents a ARRAY type.
     */
    ARRAY(3, List.class),

    /**
     * This constant represents an object type.
     */
    OBJECT(4, Object.class);

    private final int code;

    private final Class type;

    DataType(int code, Class type) {
        this.code = code;
        this.type = type;
    }
}