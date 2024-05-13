package com.mdtlabs.fhir.commonservice.common.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExceptionMessage {
    private long dateTime;
    private boolean status;
    private int errorCode;
    private String message;
    private String exception;

    @Override
    public String toString() {
        return ErrorConstants.EXCEPTION_MESSAGE +
                ErrorConstants.DATE_TIME_FIELD + dateTime +
                ErrorConstants.STATUS_FIELD + status +
                ErrorConstants.ERROR_CODE_FIELD + errorCode +
                ErrorConstants.MESSAGE_FIELD + message + ErrorConstants.FORWARD_SLASH +
                ErrorConstants.EXCEPTION_FIELD + exception + ErrorConstants.FORWARD_SLASH +
                ErrorConstants.CLOSING_BRACE;
    }
}
