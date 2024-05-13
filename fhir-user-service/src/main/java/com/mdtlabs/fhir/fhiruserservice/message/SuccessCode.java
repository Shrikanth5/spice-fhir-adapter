package com.mdtlabs.fhir.fhiruserservice.message;

import lombok.Getter;

/**
 * <p>
 * Success code to fetch message from property. Property
 * file(application.property) present in resource folder.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Getter
public enum SuccessCode {

    USER_SAVE(1001),
    USER_UPDATE(1002),
    GET_USERS(1003),
    GET_USER(1004),
    USER_DELETE(1005),
    USER_NOT_SAVED(1006),
    USER_NOT_FOUND(1007),
    SEND_EMAIL_USING_SMTP(3003),
    FORGOT_LIMIT_EXCEEDED(4010),
    SET_PASSWORD(5006),
    PING(3001),

    API_KEY_MANAGER_SAVE(6001),
    GET_API_KEY_MANAGERS(6003),
    API_KEY_MANAGER_DELETE(6005),
    API_KEY_MANAGER_NOT_FOUND(6007),
    USER_ID_DOES_NOT_EXISTS(6008),
    VALID_API_KEY(6009),
    INVALID_API_KEY(6010);


    private final int key;

    SuccessCode(int key) {
        this.key = key;
    }

}
