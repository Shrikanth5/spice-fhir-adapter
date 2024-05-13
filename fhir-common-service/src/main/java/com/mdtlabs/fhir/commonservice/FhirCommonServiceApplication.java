package com.mdtlabs.fhir.commonservice;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The entry point class for the FHIR Common Service application.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@SpringBootApplication
@ComponentScan(Constants.PACKAGE_FHIR_SERVICE)
public class FhirCommonServiceApplication {

    /**
     * The main method that starts the FHIR Common Service application.
     *
     * @param args Command-line arguments.
     *             Author: Akash Gopinath
     *             Created on: February 26, 2024
     */
    public static void main(String[] args) {
        SpringApplication.run(FhirCommonServiceApplication.class, args);
    }

}
