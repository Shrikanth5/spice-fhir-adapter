package com.mdtlabs.fhir.fhirlistenerservice.service.impl;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.helper.HelperService;
import com.mdtlabs.fhir.commonservice.common.model.dto.Message;
import com.mdtlabs.fhir.commonservice.common.model.entity.SpiceMessageTracker;
import com.mdtlabs.fhir.commonservice.common.model.enumeration.MessageStatusCode;
import com.mdtlabs.fhir.fhirlistenerservice.apiinterface.FhirAdapterAPIInterface;
import com.mdtlabs.fhir.fhirlistenerservice.repository.SpiceMessageTrackerRepository;
import feign.FeignException;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Unit Test case for the SpiceDataExchangeService for sending Spice messages to the FHIR Adapter service.
 * <p>
 * Author: Hemavardhini Jothi
 * Created on February 26, 2024
 */

@ContextConfiguration(classes = {SpiceDataExchangeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SpiceDataExchangeServiceImplTest {
    @MockBean
    private FhirAdapterAPIInterface fhirAdapterAPIInterface;

    @MockBean
    private HelperService helperService;

    @Autowired
    private SpiceDataExchangeServiceImpl spiceDataExchangeServiceImpl;

    @MockBean
    private SpiceMessageTrackerRepository spiceMessageTrackerRepository;

    /**
     * Method under test:
     * {@link SpiceDataExchangeServiceImpl#sendDataToFhirAdapter(Message)}
     */
    @Test
    void testSendDataToFhirAdapter() throws FeignException {
        // Arrange
        when(fhirAdapterAPIInterface.processMessageRequest(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(new HashMap<>());

        SpiceMessageTracker spiceMessageTracker = new SpiceMessageTracker();
        spiceMessageTracker.setActive(Boolean.TRUE);
        spiceMessageTracker
                .setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        spiceMessageTracker.setDeduplicationId(TestConstants.FOURTY_TWO);
        spiceMessageTracker.setDeleted(Boolean.TRUE);
        spiceMessageTracker.setId(TestConstants.ONE_LONG);
        spiceMessageTracker.setMessage(TestConstants.MESSAGE);
        spiceMessageTracker.setStatus(MessageStatusCode.RECEIVED);
        spiceMessageTracker
                .setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        when(spiceMessageTrackerRepository.save(Mockito.<SpiceMessageTracker>any())).thenReturn(spiceMessageTracker);
        when(spiceMessageTrackerRepository.findByDeduplicationId(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(helperService.getAccessSecretKey()).thenReturn(new HashMap<>());

        Message message = new Message();
        message.setBody(TestConstants.MESSAGE);
        message.setDeduplicationId(TestConstants.FOURTY_TWO);

        // Act
        spiceDataExchangeServiceImpl.sendDataToFhirAdapter(message);

        // Assert
        verify(helperService, atLeast(TestConstants.ONE)).getAccessSecretKey();
        verify(fhirAdapterAPIInterface).processMessageRequest(isNull(), isNull(), eq(TestConstants.MESSAGE));
        verify(spiceMessageTrackerRepository).findByDeduplicationId(eq(TestConstants.FOURTY_TWO));
        verify(spiceMessageTrackerRepository).save(Mockito.<SpiceMessageTracker>any());
    }

    /**
     * Method under test:
     * {@link SpiceDataExchangeServiceImpl#sendDataToFhirAdapter(Message)}
     */
    @Test
    void testSendDataToFhirAdapterDuplicateCheck() {
        // Arrange
        SpiceMessageTracker spiceMessageTracker = new SpiceMessageTracker();
        spiceMessageTracker.setActive(Boolean.TRUE);
        spiceMessageTracker
                .setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        spiceMessageTracker.setDeduplicationId(TestConstants.FOURTY_TWO);
        spiceMessageTracker.setDeleted(Boolean.TRUE);
        spiceMessageTracker.setId(TestConstants.ONE_LONG);
        spiceMessageTracker.setMessage(TestConstants.MESSAGE);
        spiceMessageTracker.setStatus(MessageStatusCode.RECEIVED);
        spiceMessageTracker
                .setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));

        SpiceMessageTracker spiceMessageTracker2 = new SpiceMessageTracker();
        spiceMessageTracker2.setActive(Boolean.TRUE);
        spiceMessageTracker2
                .setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        spiceMessageTracker2.setDeduplicationId(TestConstants.FOURTY_TWO);
        spiceMessageTracker2.setDeleted(Boolean.TRUE);
        spiceMessageTracker2.setId(TestConstants.ONE_LONG);
        spiceMessageTracker2.setMessage(TestConstants.MESSAGE);
        spiceMessageTracker2.setStatus(MessageStatusCode.RECEIVED);
        spiceMessageTracker2
                .setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));

        ArrayList<SpiceMessageTracker> spiceMessageTrackerList = new ArrayList<>();
        spiceMessageTrackerList.add(spiceMessageTracker2);
        when(spiceMessageTrackerRepository.save(Mockito.<SpiceMessageTracker>any())).thenReturn(spiceMessageTracker);
        when(spiceMessageTrackerRepository.findByDeduplicationId(Mockito.<String>any()))
                .thenReturn(spiceMessageTrackerList);

        Message message = new Message();
        message.setBody(TestConstants.MESSAGE);
        message.setDeduplicationId(TestConstants.FOURTY_TWO);

        // Act and Assert
        assertThrows(DataConflictException.class, () -> spiceDataExchangeServiceImpl.sendDataToFhirAdapter(message));
        verify(spiceMessageTrackerRepository).findByDeduplicationId(eq(TestConstants.FOURTY_TWO));
        verify(spiceMessageTrackerRepository).save(Mockito.<SpiceMessageTracker>any());
    }

    /**
     * Method under test:
     * {@link SpiceDataExchangeServiceImpl#sendDataToFhirAdapter(Message)}
     */
    @Test
    void testSendDataToFhirAdapterNonDuplicate() throws FeignException {
        // Arrange
        when(fhirAdapterAPIInterface.processMessageRequest(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenReturn(new HashMap<>());

        SpiceMessageTracker spiceMessageTracker = new SpiceMessageTracker();
        spiceMessageTracker.setActive(Boolean.TRUE);
        spiceMessageTracker
                .setCreatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        spiceMessageTracker.setDeduplicationId(TestConstants.FOURTY_TWO);
        spiceMessageTracker.setDeleted(Boolean.TRUE);
        spiceMessageTracker.setId(TestConstants.ONE_LONG);
        spiceMessageTracker.setMessage(TestConstants.MESSAGE);
        spiceMessageTracker.setStatus(MessageStatusCode.RECEIVED);
        spiceMessageTracker
                .setUpdatedAt(Date.from(now().atZone(ZoneOffset.UTC).toInstant()));
        when(spiceMessageTrackerRepository.save(Mockito.<SpiceMessageTracker>any())).thenReturn(spiceMessageTracker);
        when(spiceMessageTrackerRepository.findByDeduplicationId(Mockito.<String>any())).thenReturn(new ArrayList<>());
        when(helperService.getAccessSecretKey()).thenReturn(new HashMap<>());
        Message message = mock(Message.class);
        when(message.getBody()).thenReturn(TestConstants.MESSAGE);
        when(message.getDeduplicationId()).thenReturn("");
        doNothing().when(message).setBody(Mockito.<String>any());
        doNothing().when(message).setDeduplicationId(Mockito.<String>any());
        message.setBody(TestConstants.MESSAGE);
        message.setDeduplicationId(TestConstants.FOURTY_TWO);

        // Act
        spiceDataExchangeServiceImpl.sendDataToFhirAdapter(message);

        // Assert
        verify(helperService, atLeast(TestConstants.ONE)).getAccessSecretKey();
        verify(message).getBody();
        verify(message).getDeduplicationId();
        verify(message).setBody(eq(TestConstants.MESSAGE));
        verify(message).setDeduplicationId(eq(TestConstants.FOURTY_TWO));
        verify(fhirAdapterAPIInterface).processMessageRequest(isNull(), isNull(), eq(TestConstants.MESSAGE));
        verify(spiceMessageTrackerRepository).findByDeduplicationId(isNull());
        verify(spiceMessageTrackerRepository).save(Mockito.<SpiceMessageTracker>any());
    }

    @Test
    void testsendDataToFhirAdapterFeignException(){

        Message message = new Message();
        message.setBody(TestConstants.MESSAGE);
        message.setDeduplicationId(TestConstants.FOURTY_TWO);

        when(fhirAdapterAPIInterface.processMessageRequest(Mockito.<String>any(), Mockito.<String>any(),
                Mockito.<String>any())).thenThrow(FeignException.class);
        //then
        Assertions.assertThrows(Exception.class,
                                () ->spiceDataExchangeServiceImpl.sendDataToFhirAdapter(message));
    }

}
