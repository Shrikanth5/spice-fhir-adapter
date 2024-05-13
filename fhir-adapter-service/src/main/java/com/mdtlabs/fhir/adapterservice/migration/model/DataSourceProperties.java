package com.mdtlabs.fhir.adapterservice.migration.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Data source properties.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    private DataSourceConfig source;
    private DataSourceConfig target;

    /**
     * The type Data source config.
     */
    @Data
    public static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
}
