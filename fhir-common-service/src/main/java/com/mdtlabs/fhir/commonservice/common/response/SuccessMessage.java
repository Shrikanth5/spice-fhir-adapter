package com.mdtlabs.fhir.commonservice.common.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * Generic success message object.
 * </p>
 *
 * @param <T> -
 *            Author: Akash Gopinath
 *            Created on: February 26, 2024
 */
@Setter
@Getter
@Data
public class SuccessMessage<T> {
    private String message;
    private Object entity;
    private boolean status;
    private List<T> entityList;
    private Integer responseCode;
    private Long totalCount;

    /**
     * <p>
     * Success message.
     * </p>
     *
     * @param status       status is passed in this attribute.
     * @param message      Message to be displayed to the user is passed in this
     *                     attribute.
     * @param entity       object is passed in this attribute.
     * @param entityList   {@link List<T>}list is passed in this attribute.
     * @param responseCode response code is passed in this attribute.
     * @param totalCount   total count is passed in this attribute.
     */
    public SuccessMessage(boolean status, String message, Object entity, List<T> entityList, Integer responseCode,
                          Long totalCount) {
        this.setMessage(message);
        this.setEntity(entity);
        this.setEntityList(entityList);
        this.setStatus(status);
        this.setResponseCode(responseCode);
        this.setTotalCount(totalCount);
    }

}
