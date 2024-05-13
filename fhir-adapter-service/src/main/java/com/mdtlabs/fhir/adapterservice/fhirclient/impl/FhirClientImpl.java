package com.mdtlabs.fhir.adapterservice.fhirclient.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import ca.uhn.fhir.rest.client.interceptor.SimpleRequestHeaderInterceptor;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.helper.HelperService;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import static org.hl7.fhir.instance.model.api.IAnyResource.RES_ID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * <p>
 * An interface representing a client for interacting with the HAPI FHIR server.
 * This interface defines methods for sending resources, updating patient IDs, and retrieving resource details.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@Service
public class FhirClientImpl implements FhirClient {
    private final FhirContext fhirContext;
    private final RestTemplate restTemplate;
    private final HelperService helperService;
    @Value("${spring.services.fhir-server-base-url}")
    private String serverBaseUrl;
    @Value("${spring.services.credentials.fhir_accessKeyId}")
    private String accessKey;

    @Value("${spring.services.credentials.fhir_secretKey}")
    private String secretKey;

    public FhirClientImpl(FhirContext fhirContext, RestTemplate restTemplate, HelperService helperService) {
        this.fhirContext = fhirContext;
        this.restTemplate = restTemplate;
        this.helperService = helperService;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T sendResourceToFHIR(IBaseResource resource) throws FhirClientConnectionException {

        Map<String, String> authHeaders = helperService.getAccessSecretKey();
        IParser parser = fhirContext.newJsonParser();
        String fhirJson = parser.encodeResourceToString(resource);
        Logger.logInfo(resource.getClass() + Constants.JSON_BEFORE_HAPI_JPA, fhirJson);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        authHeaders.forEach(headers::add);
        HttpEntity<String> requestEntity = new HttpEntity<>(fhirJson, headers);
        return (T) fhirContext.newJsonParser().parseResource(resource.getClass(), (restTemplate.postForEntity(serverBaseUrl + resource.fhirType(), requestEntity, String.class).getBody()));
    }

    /**
     * {@inheritDoc}
     */
    public MethodOutcome updateResource(IBaseResource resource) {
        IGenericClient client = fhirContext.newRestfulGenericClient(serverBaseUrl);
        for (Map.Entry<String, String> entry : helperService.getAccessSecretKey().entrySet()) {
            client.registerInterceptor(new SimpleRequestHeaderInterceptor(entry.getKey(), entry.getValue()));
        }
        return client.update().resource(resource).execute();
    }

    /**
     * {@inheritDoc}
     */
    public String sendBundleToHapiServer(Bundle bundle) throws FhirClientConnectionException {
        Map<String, String> authHeaders = helperService.getAccessSecretKey();
        FhirValidator validator = fhirContext.newValidator();
        ValidationResult result = validator.validateWithResult(bundle);
        if (result.isSuccessful()) {
            IParser parser = fhirContext.newJsonParser();

            String bundleJson = parser.encodeResourceToString(bundle);
            Logger.logInfo(Constants.JSON_BUNDLE_BEFORE_HAPI_JPA, bundleJson);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            authHeaders.forEach(headers::add);

            HttpEntity<String> requestEntity = new HttpEntity<>(bundleJson, headers);
            return restTemplate.postForEntity(serverBaseUrl, requestEntity, String.class).getBody();
        } else {
            String errorMessage = result.getMessages().toString();
            throw new FhirValidation(20012, errorMessage);
        }
    }

    /**
     * {@inheritDoc}
     */
    public <T> T getResourceDetails(String resourceUrl, IBaseResource resource) throws FhirClientConnectionException {
        IGenericClient client = fhirContext.newRestfulGenericClient(serverBaseUrl);
        for (Map.Entry<String, String> entry : helperService.getAccessSecretKey().entrySet()) {
            client.registerInterceptor(new SimpleRequestHeaderInterceptor(entry.getKey(), entry.getValue()));
        }
        return (T) client.read().resource(resource.getClass()).withUrl(resourceUrl).execute();
    }

    /**
     * {@inheritDoc}
     */
    public Bundle getPatientAndObservationDetails(String patientFhirId) throws FhirClientConnectionException {

        IGenericClient client = fhirContext.newRestfulGenericClient(serverBaseUrl);

        for (Map.Entry<String, String> entry : helperService.getAccessSecretKey().entrySet()) {
            client.registerInterceptor(new SimpleRequestHeaderInterceptor(entry.getKey(), entry.getValue()));
        }

        return client.search()
                .forResource(Patient.class)
                .where(RES_ID.exactly().identifier(patientFhirId))
                .revInclude(Observation.INCLUDE_SUBJECT)
                .returnBundle(Bundle.class)
                .execute();

    }

    @Override
    public String sendOrganizationToFhirServer(Organization organization) {
        try {
            FhirContext fhirContext = FhirContext.forR4();
            IGenericClient fhirClient = fhirContext.newRestfulGenericClient(serverBaseUrl);
            fhirClient.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.ACCESS_KEY_ID_PARAM, accessKey));
            fhirClient.registerInterceptor(new SimpleRequestHeaderInterceptor(Constants.SECRET_ACCESS_KEY_PARAM, secretKey));

            MethodOutcome methodOutcome = fhirClient.create().resource(organization).execute();

            return methodOutcome.getId() != null ? methodOutcome.getId().getIdPart() : null;
        } catch (Exception exception) {
            Logger.logError(exception.getMessage());
        }

        return null;
    }
}