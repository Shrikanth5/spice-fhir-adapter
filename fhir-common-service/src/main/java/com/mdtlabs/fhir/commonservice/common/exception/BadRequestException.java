package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * Represents an exception indicating a bad request, typically due to client error.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class BadRequestException extends ServicesException {

    /**
     * Constructs a new BadRequestException with default values.
     */
    public BadRequestException() {
        this(1001, BadRequestException.class.getSimpleName());
    }

    /**
     * Constructs a new BadRequestException with the given error code and a default message.
     *
     * @param code The error code.
     */
    public BadRequestException(final Integer code) {
        this(code, BadRequestException.class.getSimpleName());
    }

    /**
     * Constructs a new BadRequestException with the given message.
     *
     * @param message The error message.
     */
    public BadRequestException(final String message) {
        super(message);
    }

    /**
     * Constructs a new BadRequestException with the given error code and parameters.
     *
     * @param code   The error code.
     * @param params Additional parameters for constructing the error message.
     */
    public BadRequestException(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * Constructs a new BadRequestException with the given error code and message.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public BadRequestException(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * Constructs a new BadRequestException with the given error code and parameters list.
     *
     * @param code   The error code.
     * @param params A list of parameters for constructing the error message.
     */
    public BadRequestException(final Integer code, final List<String> params) {
        super(code, params);
    }
}
