package com.mdtlabs.fhir.commonservice.common.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignSupportConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new RetreiveMessageErrorDecoder();
    }
}