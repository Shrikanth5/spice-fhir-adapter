package com.mdtlabs.fhir.commonservice.common.model.enumeration;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import lombok.Getter;

/**
 * <p>
 * The {@code MessageStatusCode} class represents an enum that stores information about status
 * of the message from spice.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Getter
public enum MessageStatusCode {
    RECEIVED(Constants.MESSAGE_RECEIVED),
    SUCCESS(Constants.MESSAGE_SUCCESS),
    FAILURE(Constants.MESSAGE_FAILURE),
    DUPLICATE(Constants.MESSAGE_DUPLICATE);

    private final String statusCode;

    MessageStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
