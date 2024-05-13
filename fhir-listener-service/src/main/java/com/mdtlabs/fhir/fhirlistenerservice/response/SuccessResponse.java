package com.mdtlabs.fhir.fhirlistenerservice.response;

import com.mdtlabs.fhir.commonservice.common.MessageValidator;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.domain.Paged;
import com.mdtlabs.fhir.commonservice.common.response.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * SuccessResponse class provides constructors for creating success responses with messages, entities, and total counts.
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public class SuccessResponse<T> extends ResponseEntity<Object> {

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, a Paged object containing a list of entities, and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param paged        Paged<T> object containing a list of entities and a count
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, Paged<T> paged, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), paged, responseCode);
    }

    /**
     * Constructs a SuccessResponse with a message, a Paged object containing a list of entities, and an HttpStatus responseCode.
     *
     * @param message      String representing the success message
     * @param paged        Paged<T> object containing a list of entities and a count
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(String message, Paged<T> paged, HttpStatus responseCode) {
        this(message, null, paged.getList(), responseCode, paged.getCount());
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, a list of entities, and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       List<T> representing a list of entities
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), null, entity, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, a list of entities, a totalCount, and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       List<T> representing a list of entities
     * @param totalCount   long representing the total count
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, long totalCount, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), null, entity, responseCode, totalCount);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, an entity, a totalCount, and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       Object representing the entity
     * @param totalCount   long representing the total count
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, Object entity, long totalCount, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), entity, null, responseCode, totalCount);
    }

    /**
     * Constructs a SuccessResponse with a message, a list of entities, and an HttpStatus responseCode.
     *
     * @param message      String representing the success message
     * @param entity       List<T> representing a list of entities
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(String message, List<T> entity, HttpStatus responseCode) {
        this(message, null, entity, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, an entity, and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       Object representing the entity
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, Object entity, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), entity, null, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a message, an entity, and an HttpStatus responseCode.
     *
     * @param message      String representing the success message
     * @param entity       Object representing the entity
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(String message, Object entity, HttpStatus responseCode) {
        this(message, entity, null, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum and an HttpStatus responseCode.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param responseCode HttpStatus representing the HTTP response code
     */
    public SuccessResponse(SuccessCode successCode, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), null, null, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a message, an entity, a list of entities, an HttpStatus responseCode, and a totalCount.
     *
     * @param message    String representing the success message
     * @param entity     Object representing the entity
     * @param entityList List<T> representing a list of entities
     * @param httpStatus HttpStatus representing the HTTP response code
     * @param totalCount Long representing the total count
     */
    public SuccessResponse(String message, Object entity, List<T> entityList, HttpStatus httpStatus, Long totalCount) {
        super(new SuccessMessage<>(Boolean.TRUE, message, entity, entityList, httpStatus.value(), totalCount), httpStatus);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, a list of entities, an HttpStatus responseCode, and a totalCount.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       List<T> representing a list of entities
     * @param responseCode HttpStatus representing the HTTP response code
     * @param totalCount   Long representing the total count
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, HttpStatus responseCode, Long totalCount) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS), null, entity, responseCode, totalCount);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, an entity, an HttpStatus responseCode, and a variable number of arguments.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       Object representing the entity
     * @param responseCode HttpStatus representing the HTTP response code
     * @param args         String representing additional arguments
     */
    public SuccessResponse(SuccessCode successCode, Object entity, HttpStatus responseCode, String... args) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS, args), entity, null, responseCode, null);
    }

    /**
     * Constructs a SuccessResponse with a SuccessCode enum, an entity, a totalCount, an HttpStatus responseCode, and a variable number of arguments.
     *
     * @param successCode  SuccessCode enum representing the success code
     * @param entity       Object representing the entity
     * @param totalCount   Long representing the total count
     * @param responseCode HttpStatus representing the HTTP response code
     * @param args         String representing additional arguments
     */
    public SuccessResponse(SuccessCode successCode, Object entity, long totalCount, HttpStatus responseCode, String... args) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS, args), entity, null, responseCode, totalCount);
    }
}
