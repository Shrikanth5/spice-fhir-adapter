package com.mdtlabs.fhir.adapterservice.fhirclient;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;

/**
 * An interface representing a client for interacting with the HAPI FHIR server.
 * <p>
 * This interface defines methods for sending resources, updating patient IDs, and retrieving resource details.
 * </p>
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
public interface FhirClient {

    /**
     * Sends a FHIR resource to the HAPI FHIR server.
     *
     * @param <T>      the type parameter
     * @param resource The Converted FHIR {@link IBaseResource} object to be sent.
     * @return A {@link IBaseResource} object representing the response from the server.
     * @throws FhirClientConnectionException the fhir client connection exception
     */
    <T> T sendResourceToFHIR(IBaseResource resource) throws FhirClientConnectionException;

    /**
     * Sends a FHIR resources to the HAPI FHIR server for update.
     *
     * @param resource The list of {@link IBaseResource} objects to be sent.
     * @return A list of {@link IBaseResource} objects representing the response from the server.
     */
    MethodOutcome updateResource(IBaseResource resource);

    /**
     * Sends a FHIR Bundle to the HAPI FHIR server.
     *
     * @param bundle The Bundle to be sent to the server.
     * @return A String representing the response from the server.
     * @throws FhirClientConnectionException the fhir client connection exception
     */
    String sendBundleToHapiServer(Bundle bundle) throws FhirClientConnectionException;

    /**
     * Retrieves details of a specific FHIR resource from the server based on its URL.
     *
     * @param <T>         The type of the resource.
     * @param resourceUrl The URL of the FHIR resource.
     * @param resource    The IBaseResource representing the resource type.
     * @return The detailed information of the specified FHIR resource.
     * @throws FhirClientConnectionException the fhir client connection exception
     */
    <T> T getResourceDetails(String resourceUrl, IBaseResource resource) throws FhirClientConnectionException;

    /**
     * Retrieves details of Patient and Observation resources from the server based on the patient's FHIR ID.
     *
     * @param patientFhirId The FHIR ID of the patient.
     * @return A Bundle containing the details of Patient and Observation resources.
     * @throws FhirClientConnectionException the fhir client connection exception
     */
    Bundle getPatientAndObservationDetails(String patientFhirId) throws FhirClientConnectionException;

    /**
     * Send organization to fhir server string.
     *
     * @param organization the organization
     * @return the string
     */
    String sendOrganizationToFhirServer(Organization organization);

}
