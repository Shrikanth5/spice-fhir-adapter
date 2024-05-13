package com.mdtlabs.fhir.adapterservice.message.service;

/**
 * Service interface for handling messages.
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
public interface MessageService {

    /**
     * Processes the incoming message, extracts relevant data, and saves FHIR mappings accordingly.
     *
     * @param message The incoming message to be processed.
     */
    void createMappingData(String message);

    /**
     * Converts the incoming message to objects and triggers the creation of FHIR resources.
     *
     * @param message The incoming message to be converted and processed.
     * @return A message indicating the success of the processing.
     */
    String convertMessageToObject(String message);
}
