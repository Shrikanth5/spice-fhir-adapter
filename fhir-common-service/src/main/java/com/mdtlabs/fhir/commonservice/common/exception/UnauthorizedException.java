package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * Represents an exception indicating unauthorized access.
 */
public class UnauthorizedException extends ServicesException {

    /**
     * Constructs a new UnauthorizedException with default values.
     */
    public UnauthorizedException() {
        this(1001, UnauthorizedException.class.getSimpleName());
    }

    /**
     * Constructs a new UnauthorizedException with the given error code and a default message.
     *
     * @param code The error code.
     */
    public UnauthorizedException(final Integer code) {
        this(code, UnauthorizedException.class.getSimpleName());
    }

    /**
     * Constructs a new UnauthorizedException with the given message.
     *
     * @param message The error message.
     */
    public UnauthorizedException(final String message) {
        super(message);
    }

    /**
     * Constructs a new UnauthorizedException with the given error code and parameters.
     *
     * @param code   The error code.
     * @param params Additional parameters for constructing the error message.
     */
    public UnauthorizedException(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * Constructs a new UnauthorizedException with the given error code and message.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public UnauthorizedException(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * Constructs a new UnauthorizedException with the given error code and parameters list.
     *
     * @param code   The error code.
     * @param params A list of parameters for constructing the error message.
     */
    public UnauthorizedException(final Integer code, final List<String> params) {
        super(code, params);
    }
}
