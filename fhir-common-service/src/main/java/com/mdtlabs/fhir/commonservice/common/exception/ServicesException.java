package com.mdtlabs.fhir.commonservice.common.exception;

import com.mdtlabs.fhir.commonservice.common.MessageValidator;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

/**
 * Represents a generic exception for handling service-related errors.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Getter
public class ServicesException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6918269662648545345L;
    private final Integer code;
    private final String message;

    /**
     * Constructs a ServicesException object with the provided error code.
     *
     * @param code The error code.
     */
    public ServicesException(final Integer code) {
        this.code = code;
        this.message = this.getClass().getSimpleName();
    }

    /**
     * Constructs a ServicesException object with the provided error message.
     *
     * @param message The error message.
     */
    public ServicesException(final String message) {
        this.code = null;
        this.message = message;
    }

    /**
     * Constructs a ServicesException object with the provided error code and message.
     *
     * @param code    The error code.
     * @param message The error message.
     */
    public ServicesException(final Integer code, final String message) {
        this.code = code;
        this.message = MessageValidator.getInstance().getMessage(code.toString(), Constants.ERROR, message);
    }

    /**
     * Constructs a ServicesException object with the provided error code and dynamic parameters.
     *
     * @param code   The error code.
     * @param params Additional parameters for constructing the error message.
     */
    public ServicesException(final Integer code, final String... params) {
        this.code = code;
        this.message = MessageValidator.getInstance().getMessage(code.toString(), Constants.ERROR, params);
    }

    /**
     * Constructs a ServicesException object with the provided error code and parameters list.
     *
     * @param code   The error code.
     * @param params A list of parameters for constructing the error message.
     */
    public ServicesException(final Integer code, final List<String> params) {
        this.code = code;
        this.message = MessageValidator.getInstance().getMessage(code.toString(), Constants.ERROR, params);
    }

    /**
     * Retrieves the error message associated with this exception.
     *
     * @return The error message.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
