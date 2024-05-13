package com.mdtlabs.fhir.fhirlistenerservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.helper.HelperService;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.Message;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceMessageTracker;
import com.mdtlabs.fhir.commonservice.common.model.enumeration.MessageStatusCode;
import com.mdtlabs.fhir.fhirlistenerservice.apiinterface.FhirAdapterAPIInterface;
import com.mdtlabs.fhir.fhirlistenerservice.repository.SpiceMessageTrackerRepository;
import com.mdtlabs.fhir.fhirlistenerservice.service.SpiceDataExchangeService;
import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the SpiceDataExchangeService for sending Spice messages to the FHIR Adapter service.
 * This service handles sending messages to the FHIR Adapter and tracks message statuses.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 * created on August 17, 2023
 */
@Service
public class SpiceDataExchangeServiceImpl implements SpiceDataExchangeService {

    private final FhirAdapterAPIInterface fhirAdapterAPIInterface;

    private final SpiceMessageTrackerRepository spiceMessageTrackerRepository;

    private final HelperService helperService;

    public SpiceDataExchangeServiceImpl(FhirAdapterAPIInterface fhirAdapterAPIInterface,
                                        SpiceMessageTrackerRepository spiceMessageTrackerRepository,
                                        HelperService helperService) {
        this.fhirAdapterAPIInterface = fhirAdapterAPIInterface;
        this.spiceMessageTrackerRepository = spiceMessageTrackerRepository;
        this.helperService = helperService;
    }

    /**
     * Static method to build the SpiceMessageTracker with message and deduplicateId.
     *
     * @param message                    object from sqsQueue
     * @param spiceMessageTrackerBuilder object reference
     * @param deduplicationId            deduplication ID for the message
     */
    private static void setSpiceMessageTrackerObject(Message message,
                                                     SpiceMessageTracker.SpiceMessageTrackerBuilder<?, ?> spiceMessageTrackerBuilder,
                                                     String deduplicationId) {
        spiceMessageTrackerBuilder
                .status(MessageStatusCode.RECEIVED)
                .message(message.getBody())
                .deduplicationId(deduplicationId)
                .isActive(Boolean.TRUE)
                .isDeleted(Boolean.FALSE);
    }

    /**
     * Private method used to handle duplicate messages that are already processed.
     *
     * @param spiceMessageTrackerBuilder object from message
     */
    private void handleDuplicateMessage(SpiceMessageTracker.SpiceMessageTrackerBuilder<?, ?> spiceMessageTrackerBuilder) {
        spiceMessageTrackerBuilder
                .isActive(Boolean.TRUE)
                .status(MessageStatusCode.DUPLICATE);
        SpiceMessageTracker spiceMessageTracker = spiceMessageTrackerBuilder.build();
        spiceMessageTrackerRepository.save(spiceMessageTracker);
        Logger.logError(Constants.DUPLICATE_NOT_SENT_TO_ADAPTER);
        throw new DataConflictException(20011, Constants.DUPLICATE_NOT_SENT_TO_ADAPTER);
    }

    @Override
    public void sendDataToFhirAdapter(Message message) {
        try {
            SpiceMessageTracker.SpiceMessageTrackerBuilder<?, ?> spiceMessageTrackerBuilder = SpiceMessageTracker.builder();
            String deduplicationId = StringUtils.isNotBlank(message.getDeduplicationId()) ? message.getDeduplicationId() : null;
            setSpiceMessageTrackerObject(message, spiceMessageTrackerBuilder, deduplicationId);
            List<SpiceMessageTracker> deduplicationIdList = spiceMessageTrackerRepository.findByDeduplicationId(deduplicationId);
            if (!deduplicationIdList.isEmpty()) {
                handleDuplicateMessage(spiceMessageTrackerBuilder);
            }
            Logger.logInfo(Constants.POST_CALL_TO_FHIR_ADAPTER, message);
            SpiceMessageTracker spiceMessageTracker = spiceMessageTrackerBuilder.build();
            spiceMessageTrackerRepository.save(spiceMessageTracker);
            Map<String, Object> response = fhirAdapterAPIInterface.processMessageRequest(
                    helperService.getAccessSecretKey().get(Constants.ACCESS_KEY_ID_PARAM),
                    helperService.getAccessSecretKey().get(Constants.SECRET_ACCESS_KEY_PARAM),
                    spiceMessageTracker.getMessage());
            Logger.logInfo(Constants.FHIR_ADAPTER_RESPONSE, response);
        } catch (FeignException feignException) {
            Logger.logError(feignException.getMessage());
            throw new FhirValidation(feignException.getMessage());
        }
    }
}
