package com.mdtlabs.fhir.commonservice.common;

import com.mdtlabs.fhir.commonservice.common.exception.ServicesException;

import java.util.List;

/**
 * <p>
 * The DataConflictException class is a subclass of ServicesException that handles exceptions related
 * to data conflicts.
 * </p>
 */
public class DataConflictException extends ServicesException {
    /**
     * <p>
     * Constructor method for DataConflictException class with default error code and message.
     * </p>
     */
    public DataConflictException() {
        this(1001, DataConflictException.class.getSimpleName());
    }

    /**
     * <p>
     * Constructor method for DataConflictException class with specified error code and default message.
     * </p>
     *
     * @param code Integer value of error code
     */
    public DataConflictException(final Integer code) {
        this(code, DataConflictException.class.getSimpleName());
    }

    /**
     * <p>
     * Java constructor for DataConflictException with an Integer code argument, calls constructor with default message.
     * </p>
     *
     * @param message code param is given
     */
    public DataConflictException(final String message) {
        super(message);
    }

    /**
     * <p>
     * Constructor method for DataConflictException class with specified error code and variable message parameters.
     * </p>
     *
     * @param code   Integer value of error code
     * @param params List of error message parameters
     */
    public DataConflictException(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * <p>
     * Constructor method for DataConflictException class with specified error code and message.
     * </p>
     *
     * @param code    Integer value of error code
     * @param message Error message
     */
    public DataConflictException(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * <p>
     * Constructor method for DataConflictException class with specified error code and list of message parameters.
     * </p>
     *
     * @param code   Integer value of error code
     * @param params List of error message parameters
     */
    public DataConflictException(final Integer code, final List<String> params) {
        super(code, params);
    }

}
