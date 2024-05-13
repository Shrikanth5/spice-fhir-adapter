package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.SimpleRequestHeaderInterceptor;
import org.hl7.fhir.r4.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mdtlabs.fhir.adapterservice.migration.service.FhirOrganizationService;
import com.mdtlabs.fhir.adapterservice.migration.utils.Constants;

/**
 * The type Organization service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@Service
public class FhirOrganizationServiceImpl implements FhirOrganizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FhirOrganizationServiceImpl.class);

    @Value("${spring.services.fhir-server-base-url}")
    private String fhirServerBaseUrl;

    @Value("${spring.services.credentials.fhir_accessKeyId}")
    private String accessKeyId;

    @Value("${spring.services.credentials.fhir_secretKey}")
    private String secretAccessKey;

    @Override
    public String sendOrganizationsToFhirServer(Organization organization) {
        try {

            FhirContext fhirContext = FhirContext.forR4();
            IGenericClient fhirClient = fhirContext.newRestfulGenericClient(fhirServerBaseUrl);
            fhirClient.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.ACCESS_KEY, accessKeyId));
            fhirClient.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.SECRET_KEY, secretAccessKey));

            MethodOutcome methodOutcome = fhirClient.create().resource(organization).execute();

            return methodOutcome.getId() != null ? methodOutcome.getId().getIdPart() : null;
        } catch (Exception exception) {
            LOGGER.error(exception.getMessage());
        }

        return null;
    }
}
