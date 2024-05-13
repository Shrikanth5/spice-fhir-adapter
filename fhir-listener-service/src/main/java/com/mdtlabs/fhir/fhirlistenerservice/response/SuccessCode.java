package com.mdtlabs.fhir.fhirlistenerservice.response;

/**
 * <p>
 * Success code to fetch message from property. Property
 * file(application.property) present in resource folder.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public enum SuccessCode {

    PING(1000),
    SUCCESS(1001);

    private final int key;

    SuccessCode(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
