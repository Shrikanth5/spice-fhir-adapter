package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * <p>
 * This class is used to handle the DataNotFoundException.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class DataNotFoundException extends ServicesException {

    /**
     * <p>
     * This method is used to generate generic exception
     * </p>
     */
    public DataNotFoundException() {
        this(1001, DataNotFoundException.class.getSimpleName());
    }

    /**
     * <p>
     * Constructor method for the DataNotFoundException class.
     * Creates an instance of DataNotFoundException with a default message based on the provided code.
     * </p>
     *
     * @param code Integer parameter representing the error code.
     */
    public DataNotFoundException(final Integer code) {
        this(code, DataNotFoundException.class.getSimpleName());
    }

    /**
     * <p>
     * Java constructor for DataNotFoundException with an Integer code argument, calls constructor with default message.
     * </p>
     *
     * @param message code param is given
     */
    public DataNotFoundException(final String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor method for the DataNotFoundException class.
     * Creates an instance of DataNotFoundException with a custom message based on the provided code and params.
     * </p>
     *
     * @param code   Integer parameter representing the error code.
     * @param params Variable number of String parameters providing additional information about the exception.
     */
    public DataNotFoundException(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * <p>
     * Constructor method for the DataNotFoundException class.
     * Creates an instance of DataNotFoundException with a custom message based on the provided code and message.
     * </p>
     *
     * @param code    Integer parameter representing the error code.
     * @param message String parameter providing a custom message for the exception.
     */
    public DataNotFoundException(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * <p>
     * Constructor method for the DataNotFoundException class.
     * Creates an instance of DataNotFoundException with a custom message based on the provided code and params.
     * </p>
     *
     * @param code   Integer parameter representing the error code.
     * @param params List of String parameters providing additional information about the exception.
     */
    public DataNotFoundException(final Integer code, final List<String> params) {
        super(code, params);
    }
}
