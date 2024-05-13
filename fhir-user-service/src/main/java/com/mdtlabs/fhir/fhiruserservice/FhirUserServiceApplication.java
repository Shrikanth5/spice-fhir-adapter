package com.mdtlabs.fhir.fhiruserservice;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * The entry point class for the FHIR User Service application.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan(Constants.PACKAGE_CORE_PLATFORM)
@EnableFeignClients
public class FhirUserServiceApplication {

    /**
     * <p>
     * The main method that starts the FHIR User Service application.
     * </p>
     *
     * @param args Command-line arguments.
     * @author AkashGopinath created on August 17, 2023
     */
    public static void main(String[] args) {
        SpringApplication.run(FhirUserServiceApplication.class, args);
    }

}
