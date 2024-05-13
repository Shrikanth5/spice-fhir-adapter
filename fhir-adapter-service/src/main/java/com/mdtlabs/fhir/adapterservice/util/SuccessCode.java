package com.mdtlabs.fhir.adapterservice.util;

import lombok.Getter;

/**
 * <p>
 * Success code to fetch message from property. Property
 * file(application.property) present in resource folder.
 * </p>
 * <p>
 * Author: Charan
 * Created on: February 27, 2024
 */
@Getter
public enum SuccessCode {

    ENROLLMENT_SAVE(1001),
    ASSESSMENT_SAVE(1002),
    USER_SAVE(1003),
    ROLE_SAVE(1005),
    MESSAGE_SAVE(1004),
    PATIENT_UPDATE(1006),
    PING(1000);


    private final int key;

    SuccessCode(int key) {
        this.key = key;
    }

}
