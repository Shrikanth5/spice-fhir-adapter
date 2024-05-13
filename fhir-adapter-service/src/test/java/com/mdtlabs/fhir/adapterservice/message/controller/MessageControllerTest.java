package com.mdtlabs.fhir.adapterservice.message.controller;

import com.mdtlabs.fhir.adapterservice.message.service.MessageService;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @Mock
    MessageService messageService;

    @Test
    void getMessageTest() {
        SuccessResponse<String> response = messageController.getMessage(Constants.ACCESS_KEY_ID_PARAM, Constants.SECRET_ACCESS_KEY_PARAM);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void processMessageRequestTest() {
        //when
        when(messageService.convertMessageToObject(Constants.MESSAGE)).thenReturn(Constants.MESSAGE);
        SuccessResponse<String> response = messageController.processMessageRequest(Constants.ACCESS_KEY_ID_PARAM, Constants.SECRET_ACCESS_KEY_PARAM, Constants.MESSAGE);

        verify(messageService).convertMessageToObject(Constants.MESSAGE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
