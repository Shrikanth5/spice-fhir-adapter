package com.mdtlabs.fhir.commonservice.common.helper;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class providing helper methods for accessing credentials.
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Service
public class HelperService {

    @Value("${spring.services.credentials.fhir_accessKeyId}")
    private String accessKey;

    @Value("${spring.services.credentials.fhir_secretKey}")
    private String secretKey;

    /**
     * Retrieves the access and secret keys as a map.
     *
     * @return A map containing the access key and secret key.
     */
    public Map<String, String> getAccessSecretKey() {
        Map<String, String> headers = new HashMap<>();

        headers.put(Constants.ACCESS_KEY_ID_PARAM, accessKey);
        headers.put(Constants.SECRET_ACCESS_KEY_PARAM, secretKey);
        return headers;
    }
}
