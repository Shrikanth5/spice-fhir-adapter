package com.mdtlabs.fhir.adapterservice.message.controller;

import com.mdtlabs.fhir.adapterservice.message.service.MessageService;
import com.mdtlabs.fhir.adapterservice.util.SuccessCode;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling messages.
 * <p>
 * Author: Shrikanth
 * <p>
 * Created on: February 27, 2024
 */
@RestController
@RequestMapping(Constants.MESSAGE_URI)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Retrieves a ping message.
     *
     * @param accessKey The access key ID for authentication.
     * @param secretKey The secret access key for authentication.
     * @return A success message for receiving a ping.
     */
    @GetMapping(Constants.PING)
    public SuccessResponse<String> getMessage(@RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
                                              @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey) {
        return new SuccessResponse<>(SuccessCode.PING, Constants.ADAPTER_SERVICE_PONG, HttpStatus.OK);
    }

    /**
     * Receives and processes a message, creating mapping data.
     *
     * @param accessKey The access key ID for authentication.
     * @param secretKey The secret access key for authentication.
     * @param message   The message to be processed.
     * @return A success response with the converted message object.
     * @throws Exception If an exception occurs during the message processing.
     */
    @PostMapping(Constants.RECEIVE_MESSAGE_URI)
    public SuccessResponse<String> processMessageRequest(
            @RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
            @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
            @RequestBody String message) {
        messageService.createMappingData(message);
        return new SuccessResponse<>(SuccessCode.MESSAGE_SAVE, messageService.convertMessageToObject(message),
                HttpStatus.OK);
    }
}
