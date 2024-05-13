package com.mdtlabs.fhir.fhirlistenerservice.apiinterface;

import com.mdtlabs.fhir.commonservice.common.config.FeignSupportConfig;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * <p>
 * Feign client interface for interacting with the FHIR Adapter service.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@FeignClient(name = Constants.FHIR_ADAPTER_SERVICE,
        url = "${spring.services.fhir-adapter-service-base-url}${FHIR_ADAPTER_CONTEXT_PATH}",
        configuration = FeignSupportConfig.class)
public interface FhirAdapterAPIInterface {

    /**
     * POST request to the FHIR Adapter service's "/message/receive-message" endpoint.
     *
     * @param accessKey The access key ID for authentication.
     * @param secretKey The secret access key for authentication.
     * @param request   The message request payload to be sent.
     * @return The response from the FHIR Adapter service.
     * <p>
     * Author: Akash Gopinath
     * Created on: February 12, 2024
     */
    @PostMapping(Constants.PROCESS_MESSAGE_REQUEST_FROM_LISTENER)
    Map<String, Object> processMessageRequest(@RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
                                              @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
                                              @RequestBody String request) throws FeignException;
}
