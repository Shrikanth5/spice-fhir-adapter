package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * Represents an exception for validation-related errors.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class Validation extends ServicesException {

    /**
     * Constructs a new Validation exception with default values.
     */
    public Validation() {
        this(1001, Validation.class.getSimpleName());
    }

    /**
     * Constructs a new Validation exception with the given error code and a default message.
     *
     * @param code The error code.
     */
    public Validation(final Integer code) {
        this(code, Validation.class.getSimpleName());
    }

    /**
     * Constructs a new Validation exception with the given error code and parameters.
     *
     * @param code   The error code.
     * @param params Additional parameters for constructing the error message.
     */
    public Validation(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * Constructs a new Validation exception with the given error code and message.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public Validation(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * Constructs a new Validation exception with the given error code and parameters list.
     *
     * @param code   The error code.
     * @param params A list of parameters for constructing the error message.
     */
    public Validation(final Integer code, final List<String> params) {
        super(code, params);
    }
}
