package com.mdtlabs.fhir.adapterservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;

/**
 * The entry point class for the FHIR Adapter Service application.
 * <p>
 * Author: Charan created on August 17, 2023
 */
@SpringBootApplication(proxyBeanMethods = false)
@ComponentScan(value = Constants.PACKAGE_CORE_PLATFORM)
public class AdapterServiceApplication {

    /**
     * The main method that starts the FHIR Adapter Service application.
     *
     * @param args Command-line arguments.
     *             Author: charan created on August 17, 2023
     */
    public static void main(String[] args) {
        SpringApplication.run(AdapterServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
