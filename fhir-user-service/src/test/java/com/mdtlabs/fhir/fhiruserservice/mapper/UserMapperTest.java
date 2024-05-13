package com.mdtlabs.fhir.fhiruserservice.mapper;


import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;

import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserMapper.class})
@ExtendWith(SpringExtension.class)
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    /**
     * Method under test: {@link UserMapper#setExistingUser(User, User)}
     */
    @Test
    void testSetExistingUser() {

        // Arrange
        UserMapper userMapper = new UserMapper();

        Country country = TestDataProvider.getCountry();
        Timezone timezone = TestDataProvider.getTimezone();
        User updatedUser = mock(User.class);
        when(updatedUser.getCountry()).thenReturn(country);
        when(updatedUser.getTimezone()).thenReturn(timezone);
        Country country2 = TestDataProvider.getCountry();
        Timezone timezone2 = TestDataProvider.getTimezone();
        User existingUser = mock(User.class);
        when(existingUser.getCountry()).thenReturn(country2);
        when(existingUser.getTimezone()).thenReturn(timezone2);
        userMapper.setExistingUser(updatedUser, existingUser);
        verify(updatedUser).getCountry();
    }

    @Test
    void setExistingUserFieldsUpdateTest() {
        //given
        User updatedUser = TestDataProvider.getUser();
        //when
        User existingUser = new User();
        userMapper.setExistingUser(updatedUser, existingUser);
        //then
        assertEquals(TestConstants.NAME, updatedUser.getUsername());
        assertEquals(TestConstants.PASSWORD, updatedUser.getPassword());
        assertEquals(TestConstants.NAME, updatedUser.getCompanyName());
        assertEquals(TestConstants.COUNTRY_CODE, updatedUser.getCountryCode());
        assertEquals(TestConstants.ADDRESS, updatedUser.getAddress());
        assertEquals(TestConstants.NAME, updatedUser.getFirstName());
        assertEquals(Constants.MIDDLE_NAME, updatedUser.getMiddleName());
        assertEquals(Constants.LAST_NAME, updatedUser.getLastname());
        assertEquals(TestConstants.EMAIL, updatedUser.getCompanyEmail());
        assertEquals(Constants.WEBSITE, updatedUser.getWebsite());
        assertEquals(Constants.PHONE, updatedUser.getPhone());
    }

    @Test
    void setExistingUserFieldsUpdatePropertyValueNullTest() {
        //given
        User updatedUser = TestDataProvider.getUser();
        //when
        User existingUser = new User();
        userMapper.setExistingUser(existingUser, updatedUser);
        //then
        assertEquals(TestConstants.NAME, updatedUser.getUsername());
        assertEquals(TestConstants.PASSWORD, updatedUser.getPassword());
        assertEquals(TestConstants.NAME, updatedUser.getCompanyName());
        assertEquals(TestConstants.COUNTRY_CODE, updatedUser.getCountryCode());
        assertEquals(TestConstants.ADDRESS, updatedUser.getAddress());
        assertEquals(TestConstants.NAME, updatedUser.getFirstName());
        assertEquals(Constants.MIDDLE_NAME, updatedUser.getMiddleName());
        assertEquals(Constants.LAST_NAME, updatedUser.getLastname());
        assertEquals(TestConstants.EMAIL, updatedUser.getCompanyEmail());
        assertEquals(Constants.WEBSITE, updatedUser.getWebsite());
        assertEquals(Constants.PHONE, updatedUser.getPhone());

    }

    @Test
    void setExistingUserFieldsEqualsUpdateUserValueTest() {
        //given
        User updatedUser = TestDataProvider.getUser();
        //when
        User existingUser = TestDataProvider.getUser();
        userMapper.setExistingUser(existingUser, updatedUser);
        //then
        assertEquals(TestConstants.NAME, updatedUser.getUsername());
        assertEquals(TestConstants.PASSWORD, updatedUser.getPassword());
        assertEquals(TestConstants.NAME, updatedUser.getCompanyName());
        assertEquals(TestConstants.COUNTRY_CODE, updatedUser.getCountryCode());
        assertEquals(TestConstants.ADDRESS, updatedUser.getAddress());
        assertEquals(TestConstants.NAME, updatedUser.getFirstName());
        assertEquals(Constants.MIDDLE_NAME, updatedUser.getMiddleName());
        assertEquals(Constants.LAST_NAME, updatedUser.getLastname());
        assertEquals(TestConstants.EMAIL, updatedUser.getCompanyEmail());
        assertEquals(Constants.WEBSITE, updatedUser.getWebsite());
        assertEquals(Constants.PHONE, updatedUser.getPhone());
    }
}
