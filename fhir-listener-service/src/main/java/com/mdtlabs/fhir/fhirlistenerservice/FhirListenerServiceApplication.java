package com.mdtlabs.fhir.fhirlistenerservice;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * The entry point class for the FHIR Listener Service application.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan(Constants.PACKAGE_FHIR_SERVICE)
@EnableFeignClients
public class FhirListenerServiceApplication {

    /**
     * The main method that starts the FHIR Listener Service application.
     *
     * @param args Command-line arguments.
     * Author: Akash Gopinath
     * Created on: February 12, 2024
     */
    public static void main(String[] args) {
        SpringApplication.run(FhirListenerServiceApplication.class, args);
    }

}
