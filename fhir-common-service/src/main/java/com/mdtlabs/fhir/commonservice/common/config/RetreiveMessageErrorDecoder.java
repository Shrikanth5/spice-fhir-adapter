package com.mdtlabs.fhir.commonservice.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException ioException) {
            return new Exception(ioException.getMessage());
        }
        return switch (response.status()) {
            case 400 -> new BadRequestException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.BAD_REQUEST);
            case 401 -> new UnauthorizedException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.UNAUTHORIZED);
            case 404 -> new DataNotFoundException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.DATA_NOT_FOUND);
            case 406 -> new DataNotAcceptableException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.DATA_NOT_ACCEPTABLE);
            case 408 -> new RequestTimeOutException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.REQUEST_TIME_OUT);
            case 409 -> new DataConflictException(message.getMessage() != null ? message.getMessage() :
                    ErrorConstants.DATA_CONFLICT);
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
