package com.mdtlabs.fhir.fhiruserservice.message;

import com.mdtlabs.fhir.commonservice.common.domain.Paged;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class SuccessResponseTest {

    @Test
    void testSuccessResponseConstructor() {
        Paged<Object> paged = mock(Paged.class);
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, paged, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseConstructorMessage() {
        Paged<Object> paged = mock(Paged.class);
        SuccessResponse<Object> successResponse = new SuccessResponse<>("message", paged, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseConstructorEntity() {
        User user = mock(User.class);
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, user, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }
    @Test
    void testSuccessResponseConstructorEntityList() {
        List<Object> user = List.of(mock(User.class));
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, user, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseConstructorCountOfEntity() {
        List<Object> user = List.of(mock(User.class));
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, user,1, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseConstructorMessageEntity() {
        List<Object> user = List.of(mock(User.class));
        SuccessResponse<Object> successResponse = new SuccessResponse<>("message", user, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void successResponseConstructor() {
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseConstructorEntityCount() {
        List<Object> user = List.of(mock(User.class));
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, user, HttpStatus.OK, Long.valueOf(1));
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testCountEndSuccessResponseConstructorAdditionalArguments() {
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, new Object(), HttpStatus.OK, "arg1", "arg2");
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }


    @Test
    void testCountEndSuccessResponseConstructorAdditionalArgumentsCount() {
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, new Object(), 1,HttpStatus.OK, "arg1", "arg2");
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseWithEntity() {
        User user = mock(User.class);
        SuccessResponse<Object> successResponse = new SuccessResponse<>("message", user, HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

    @Test
    void testSuccessResponseMessageEntityCount() {
        User user = mock(User.class);
        SuccessResponse<Object> successResponse = new SuccessResponse<>(SuccessCode.USER_SAVE, user, 1,HttpStatus.OK);
        assertEquals(HttpStatus.OK, successResponse.getStatusCode());
    }

}
