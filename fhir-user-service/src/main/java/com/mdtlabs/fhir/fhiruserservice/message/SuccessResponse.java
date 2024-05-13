package com.mdtlabs.fhir.fhiruserservice.message;

import com.mdtlabs.fhir.commonservice.common.MessageValidator;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.domain.Paged;
import com.mdtlabs.fhir.commonservice.common.response.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The SuccessResponse class provides constructors for creating success responses with messages,
 * entities, and total counts.
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public class SuccessResponse<T> extends ResponseEntity<Object> {

    /**
     * This constructor creates a success response with a message, a list of entities, and a total count.
     *
     * @param successCode  Enum representing the success code
     * @param paged        Paged object containing a list of entities and a count
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, Paged<T> paged, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()),
                Constants.SUCCESS),null, paged.getList(), responseCode, paged.getCount());
    }

    /**
     * This constructor creates a success response with a message, a list of entities, and a total count.
     *
     * @param message      Custom message
     * @param paged        Paged object containing a list of entities and a count
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(String message, Paged<T> paged, HttpStatus responseCode) {
        this(message, null, paged.getList(), responseCode, paged.getCount());
    }

    /**
     * This constructor creates a success response with a message, a list of entities, and no total count.
     *
     * @param successCode  Enum representing the success code
     * @param entity       List of entities
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                null, entity, responseCode, null);
    }

    /**
     * This constructor creates a success response with a message, a list of entities, and a total count.
     *
     * @param successCode  Enum representing the success code
     * @param entity       List of entities
     * @param totalCount   Total count of entities
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, long totalCount, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                null, entity, responseCode, totalCount);
    }

    /**
     * This constructor creates a success response with a message, an entity object, and a total count.
     *
     * @param successCode  Enum representing the success code
     * @param entity       Entity object
     * @param totalCount   Total count of entities
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, Object entity, long totalCount, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                entity, null, responseCode, totalCount);
    }

    /**
     * This constructor creates a success response with a message and a list of entities.
     *
     * @param message      Custom message
     * @param entity       List of entities
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(String message, List<T> entity, HttpStatus responseCode) {
        this(message, null, entity, responseCode, null);
    }

    /**
     * This constructor creates a success response with a message and an entity object.
     *
     * @param successCode  Enum representing the success code
     * @param entity       Entity object
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, Object entity, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                entity, null, responseCode, null);
    }

    /**
     * This constructor creates a success response with a message and an entity object.
     *
     * @param message      Custom message
     * @param entity       Entity object
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(String message, Object entity, HttpStatus responseCode) {
        this(message, entity, null, responseCode, null);
    }

    /**
     * This constructor creates a success response with only a message and a response code.
     *
     * @param successCode  Enum representing the success code
     * @param responseCode HttpStatus code
     */
    public SuccessResponse(SuccessCode successCode, HttpStatus responseCode) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                null, null, responseCode, null);
    }

    /**
     * This constructor creates a success response with a message, an entity object, a list of entities,
     * and a total count.
     *
     * @param message    Custom message
     * @param entity     Entity object
     * @param entityList List of entities
     * @param httpStatus HttpStatus code
     * @param totalCount Total count of entities
     */
    public SuccessResponse(String message, Object entity, List<T> entityList, HttpStatus httpStatus,
                           Long totalCount) {
        super(new SuccessMessage<>(Boolean.TRUE, message, entity, entityList, httpStatus.value(), totalCount),
                httpStatus);
    }

    /**
     * This constructor creates a success response with a list of entities and a total count.
     *
     * @param successCode  Enum representing the success code
     * @param entity       List of entities
     * @param responseCode HttpStatus code
     * @param totalCount   Total count of entities
     */
    public SuccessResponse(SuccessCode successCode, List<T> entity, HttpStatus responseCode, Long totalCount) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS),
                null, entity, responseCode, totalCount);
    }

    /**
     * This constructor creates a success response with a message, an entity object,
     * and a variable number of additional arguments.
     *
     * @param successCode  Enum representing the success code
     * @param entity       Entity object
     * @param responseCode HttpStatus code
     * @param args         Additional arguments for message customization
     */
    public SuccessResponse(SuccessCode successCode, Object entity, HttpStatus responseCode, String... args) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS, args),
                entity, null, responseCode, null);
    }

    /**
     * This constructor creates a success response with a message, an entity object,
     * a total count, and a variable number of additional arguments.
     *
     * @param successCode  Enum representing the success code
     * @param entity       Entity object
     * @param totalCount   Total count of entities
     * @param responseCode HttpStatus code
     * @param args         Additional arguments for message customization
     */
    public SuccessResponse(SuccessCode successCode, Object entity, long totalCount, HttpStatus responseCode,
                           String... args) {
        this(MessageValidator.getInstance().getMessage(Integer.toString(successCode.getKey()), Constants.SUCCESS, args),
                entity, null, responseCode, totalCount);
    }
}
