package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.SimpleRequestHeaderInterceptor;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Practitioner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mdtlabs.fhir.adapterservice.migration.service.FhirPractitionerService;
import com.mdtlabs.fhir.adapterservice.migration.utils.Constants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;

/**
 * The type Practitioner service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Service
public class FhirPractitionerServiceImpl implements FhirPractitionerService {

    @Value("${spring.services.fhir-server-base-url}")
    private String fhirServerBaseUrl;

    @Value("${spring.services.credentials.fhir_accessKeyId}")
    private String accessKeyId;

    @Value("${spring.services.credentials.fhir_secretKey}")
    private String secretAccessKey;


    @Override
    public String savePractitionerToHapiFhirServer(Long userId, Practitioner practitioner) {
        FhirContext fhirContext = FhirContext.forR4();
        IGenericClient client = fhirContext.newRestfulGenericClient(fhirServerBaseUrl);
        client.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.ACCESS_KEY, accessKeyId));
        client.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.SECRET_KEY, secretAccessKey));
        Bundle bundle = new Bundle();
        bundle.setType(Bundle.BundleType.TRANSACTION);
        bundle.addEntry().setResource(practitioner).getRequest().setMethod(Bundle.HTTPVerb.POST);
        Bundle responseBundle = client.transaction().withBundle(bundle).execute();
        Logger.logInfo("Response from server: " + fhirContext.newJsonParser()
                .setPrettyPrint(true).encodeResourceToString(responseBundle));
        String practitionerId = responseBundle.getEntryFirstRep().getResponse().getLocation();
        String[] splitLocation = practitionerId.split("/");
        return splitLocation[1];
    }
}
