package com.mdtlabs.fhir.commonservice.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * UserContextHolderTest class has the test methods for the UserContextHolder class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
class UserContextHolderTest {
    @Test
    public void testSetUserDtoAndGetUserDto() {
        UserDTO userDto = TestDataProvider.getUserDTO();
        UserContextHolder.setUserDto(userDto);
        UserDTO retrievedUserDto = UserContextHolder.getUserDto();
        // then
        assertEquals(userDto, retrievedUserDto);
    }
}
