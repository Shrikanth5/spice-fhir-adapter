package com.mdtlabs.fhir.commonservice.common;

import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.ServicesException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.utils.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.InvalidPathException;
import java.util.List;

/**
 * <p>
 * This class is used to handle Exceptions.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@NoArgsConstructor
@ControllerAdvice
public class ServicesExceptionHandler extends ResponseEntityExceptionHandler {

    private ExceptionResolver resolver;

    /**
     * <p>
     * This function initializes an instance of the ExceptionResolverImpl class during the
     * post-construction phase.
     * </p>
     */
    @PostConstruct
    public final void initiateErrorCodeToResolver() {
        resolver = new ExceptionResolverImpl();
    }

    /**
     * <p>
     * This method handles the invalid path exception.
     * </p>
     *
     * @param runtimeException - RuntimeException
     * @return Error message
     */
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(value = InvalidPathException.class)
    @ResponseBody
    public ErrorMessage runtimeExceptionHandler(RuntimeException runtimeException) {
        Logger.logError(StringUtil.constructString(runtimeException.getClass().getName(),
                ErrorConstants.MESSAGE_GENERIC, getErrorStackString(runtimeException)));

        return resolver.resolveError(HttpStatus.INTERNAL_SERVER_ERROR, runtimeException.getMessage());
    }

    /**
     * <p>
     * Handles the invalid method Argument exception.
     * </p>
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return Error message
     */
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Logger.logError(StringUtil.constructString(ex.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(ex)));
        List<String> rejectedValues = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return handleExceptionInternal(ex, resolver.resolveError(HttpStatus.BAD_REQUEST, rejectedValues.toString()),
                headers, status, request);
    }

    /**
     * <p>
     * This method is used to handle Data Conflict Exception.
     * </p>
     *
     * @param exception DataConflictException
     * @return Error message
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataConflictException.class)
    @ResponseBody
    protected final ErrorMessage handleDataConflict(DataConflictException exception) {
        Logger.logError(StringUtil.constructString(exception.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(exception)));
        return resolver.resolveError(HttpStatus.CONFLICT, exception.getMessage());
    }

    /**
     * <p>
     * Handles data not acceptable exception.
     * </p>
     *
     * @param exception - DataNotAcceptableException Exception
     * @return ErrorMessage
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(value = DataNotAcceptableException.class)
    @ResponseBody
    protected final ErrorMessage handleDataNotAcceptable(DataNotAcceptableException exception) {
        Logger.logError(StringUtil.constructString(exception.getClass().getName(),
                ErrorConstants.MESSAGE_GENERIC, getErrorStackString(exception)));
        return resolver.resolveError(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
    }

    /**
     * <p>
     * This method handles BadRequest Exception.
     * </p>
     *
     * @param exception - BadRequestException
     * @return ErrorMessage
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    protected final ErrorMessage handleBadRequest(BadRequestException exception) {

        Logger.logError(StringUtil.constructString(exception.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(exception)));
        return resolver.resolveError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * <p>
     * This method handles DataNotFoundException.
     * </p>
     *
     * @param exception - DataNotFoundException
     * @return error message
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseBody
    protected final ErrorMessage handleDataNotFoundException(DataNotFoundException exception) {
        Logger.logError(StringUtil.constructString(exception.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(exception)));
        return resolver.resolveError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * <p>
     * This method is responsible for handling all InternalServerError occurs
     * throughout the whole application.
     * </p>
     *
     * @param exception - Exception
     * @return Error Message
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorMessage exceptionHandler(Exception exception) {
        Logger.logError(StringUtil.constructString(exception.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(exception)));

        return resolver.resolveError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    /**
     * <p>
     * This method is used to get error stack for an exception.
     * </p>
     *
     * @param error - Exception object
     * @return error Stack
     */
    private String getErrorStackString(Exception error) {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    /**
     * <p>
     * This method is used to handle custom request
     * </p>
     *
     * @param servicesException - services exception
     * @return ErrorMessage - error trace message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ServicesException.class,
            FhirValidation.class})
    @ResponseBody
    protected final ErrorMessage handleException(ServicesException servicesException) {
        Logger.logError(StringUtil.constructString(servicesException.getClass().getName(),
                ErrorConstants.MESSAGE_GENERIC, getErrorStackString(servicesException)));

        return resolver.resolveError(HttpStatus.BAD_REQUEST, servicesException.getMessage());
    }

    /**
     * <p>
     * This method is used to handle Request TimeOut error
     * </p>
     *
     * @param servicesException - services exception
     * @return ErrorMessage - error trace message
     */
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ExceptionHandler(value = RequestTimeOutException.class)
    @ResponseBody
    protected final ErrorMessage handleRequestTimeOutException(ServicesException servicesException) {
        Logger.logError(StringUtil.constructString(servicesException.getClass().getName(),
                ErrorConstants.MESSAGE_GENERIC, getErrorStackString(servicesException)));

        return resolver.resolveError(HttpStatus.REQUEST_TIMEOUT, servicesException.getMessage());
    }

    /**
     * <p>
     * This method is used to handle internal server error
     * </p>
     *
     * @param exception- passing exception
     * @return ErrorMessage - error trace message
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = Unauthorized.class)
    @ResponseBody
    public ErrorMessage authExceptionHandler(Exception exception) {
        Logger.logError(StringUtil.constructString(exception.getClass().getName(), ErrorConstants.MESSAGE_GENERIC,
                getErrorStackString(exception)));

        return resolver.resolveError(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}
