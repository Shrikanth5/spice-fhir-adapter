package com.mdtlabs.fhir.authservice;

import com.mdtlabs.fhir.commonservice.FhirCommonServiceApplication;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p>
 * The entry point class for the FHIR Auth Service application.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
@SpringBootApplication(proxyBeanMethods = false)
@EnableJpaRepositories(basePackages = Constants.PACKAGE_FHIR_PLATFORM)
@ComponentScan(value = Constants.PACKAGE_FHIR_SERVICE)
public class FhirAuthServiceApplication {

    /**
     * <p>
     * The main method that starts the FHIR Auth Service application.
     * </p>
     *
     * @param args Command-line arguments.
     * Author: Akash Gopinath
     * Created on: February 28, 2024
     */
    public static void main(String[] args) {
        SpringApplication.run(FhirCommonServiceApplication.class, args);
    }

}
