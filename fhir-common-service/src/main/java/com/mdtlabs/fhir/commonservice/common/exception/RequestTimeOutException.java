package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * Represents an exception indicating a request timeout, typically due to server delay or unavailability.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class RequestTimeOutException extends ServicesException {

    /**
     * Constructs a new RequestTimeOutException with default values.
     */
    public RequestTimeOutException() {
        this(1001, RequestTimeOutException.class.getSimpleName());
    }

    /**
     * Constructs a new RequestTimeOutException with the given error code and a default message.
     *
     * @param code The error code.
     */
    public RequestTimeOutException(final Integer code) {
        this(code, RequestTimeOutException.class.getSimpleName());
    }

    /**
     * Constructs a new RequestTimeOutException with the given message.
     *
     * @param message The error message.
     */
    public RequestTimeOutException(final String message) {
        super(message);
    }

    /**
     * Constructs a new RequestTimeOutException with the given error code and parameters.
     *
     * @param code   The error code.
     * @param params Additional parameters for constructing the error message.
     */
    public RequestTimeOutException(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * Constructs a new RequestTimeOutException with the given error code and message.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public RequestTimeOutException(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * Constructs a new RequestTimeOutException with the given error code and parameters list.
     *
     * @param code   The error code.
     * @param params A list of parameters for constructing the error message.
     */
    public RequestTimeOutException(final Integer code, final List<String> params) {
        super(code, params);
    }
}
