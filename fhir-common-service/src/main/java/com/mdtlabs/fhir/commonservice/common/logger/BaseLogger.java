package com.mdtlabs.fhir.commonservice.common.logger;


import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Logger information
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public abstract class BaseLogger {

    /**
     * <p>
     * A static logger object for the `BaseLogger` class using the
     * `LoggerFactory.getLogger()` method from the SLF4J logging framework
     * </p>
     */
    protected static Logger logger = LoggerFactory.getLogger(Constants.LOGGER);

    /**
     * <p>
     * The `protected BaseLogger() {}` is a constructor for the `BaseLogger` class.
     * </p>
     */
    protected BaseLogger() {
    }
}
