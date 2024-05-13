package com.mdtlabs.fhir.fhirlistenerservice.service;


import com.mdtlabs.fhir.commonservice.common.model.dto.Message;

/**
 * Service interface for sending Spice message data to the FHIR Adapter service.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public interface SpiceDataExchangeService {

    /**
     * Sends the provided SQS message to the FHIR Adapter service.
     *
     * @param message The SQS message to be sent to the FHIR Adapter.
     * Author: Akash Gopinath
     * Created on: February 12, 2024
     */
    void sendDataToFhirAdapter(Message message);
}
