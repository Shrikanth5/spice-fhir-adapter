package com.mdtlabs.fhir.commonservice.common.exception;

/**
 * Exception indicating a failure in validation.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class ValidationFailureException extends Exception {

    /**
     * Constructs a new ValidationFailureException with the specified detail message.
     *
     * @param message The detail message.
     */
    public ValidationFailureException(String message) {
        super(message);
    }
}
