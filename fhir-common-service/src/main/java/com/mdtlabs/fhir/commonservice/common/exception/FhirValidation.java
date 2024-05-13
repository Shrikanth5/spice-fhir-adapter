package com.mdtlabs.fhir.commonservice.common.exception;

import java.util.List;

/**
 * <p>
 * The `FhirValidation` class is a subclass of `ServicesException` that provides constructors for
 * creating instances of the class with specific error codes and additional information about the
 * error.
 * </p>
 */
public class FhirValidation extends ServicesException {

    public FhirValidation() {
        this(1001, FhirValidation.class.getSimpleName());
    }

    /**
     * <p>
     * This is a constructor for the `FhirValidation` class that takes an integer `code` as a
     * parameter. It calls another constructor of the same class `FhirValidation` with two parameters:
     * code` and `FhirValidation.class.getSimpleName()`. The `FhirValidation.class.getSimpleName()`
     * method returns the simple name of the `FhirValidation` class, which is a string representation
     * of the class name without the package name. This constructor is used to create a new instance of
     * FhirValidation` with a specific error code and a default message that provides additional
     * information about the error.
     * </p>
     */

    public FhirValidation(final Integer code) {
        this(code, FhirValidation.class.getSimpleName());
    }

    /**
     * <p>
     * This is a constructor for the `FhirValidation` class that takes an integer code and a variable
     * number of string parameters. It calls the constructor of the parent class `ServicesException`
     * with the same parameters, passing them up the inheritance chain. This constructor is used to
     * create a new instance of `FhirValidation` with a specific error code and a variable number of
     * parameters that can be used to provide additional information about the error. The use of the
     * ellipsis (`...`) in the parameter list indicates that the method can accept any number of string
     * arguments.
     * </p>
     */
    public FhirValidation(final Integer code, final String... params) {
        super(code, params);
    }

    /**
     * <p>
     * This is a constructor for the `FhirValidation` class that takes an integer code and a string
     * message as parameters. It calls the constructor of the parent class `ServicesException` with the
     * same parameters, passing them up the inheritance chain. This constructor is used to create a new
     * instance of `FhirValidation` with a specific error code and a message that provides additional
     * information about the error.
     * </p>
     */
    public FhirValidation(final Integer code, final String message) {
        super(code, message);
    }

    /**
     * <p>
     * This is a constructor for the `FhirValidation` class that takes an integer code and a list of
     * strings as parameters. It calls the constructor of the parent class `ServicesException` with the
     * same parameters, passing them up the inheritance chain. This constructor is used to create a new
     * instance of `FhirValidation` with a specific error code and a list of parameters that can be
     * used to provide additional information about the error.
     * </p>
     */
    public FhirValidation(final Integer code, final List<String> params) {
        super(code, params);
    }

    /**
     * <p>
     * This is a constructor for the `FhirValidation` class that takes an integer code and a string
     * message as parameters. It calls the constructor of the parent class `ServicesException` with the
     * same parameters, passing them up the inheritance chain. This constructor is used to create a new
     * instance of `FhirValidation` with a specific error code and a message that provides additional
     * information about the error.
     * </p>
     */
    public FhirValidation(final String message) {
        super(message);
    }

}