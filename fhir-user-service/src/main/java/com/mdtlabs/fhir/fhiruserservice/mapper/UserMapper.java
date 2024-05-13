package com.mdtlabs.fhir.fhiruserservice.mapper;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The UserMapper class contains methods for updating and setting properties of User objects,
 * as well as setting email templates for user creation and forgot password emails.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Component
public class UserMapper {

    /**
     * This method maps the properties of an existing user object with updated values.
     *
     * @param existingUser       The existing user object to be updated.
     * @param propertiesToUpdate The map containing properties and their updated values.
     */
    private static void mapObjects(User existingUser, Map<String, Object> propertiesToUpdate) {
        for (Map.Entry<String, Object> entry : propertiesToUpdate.entrySet()) {
            String propertyName = entry.getKey();
            Object propertyValue = entry.getValue();

            switch (propertyName) {
                case Constants.USERNAME:
                case Constants.PASSWORD:
                case Constants.FIRST_NAME:
                case Constants.MIDDLE_NAME:
                case Constants.LAST_NAME:
                case Constants.COMPANY_NAME:
                case Constants.COMPANY_EMAIL:
                case Constants.COUNTRY_CODE:
                case Constants.PHONE:
                case Constants.ADDRESS:
                case Constants.WEBSITE:
                    if (propertyValue instanceof String) {
                        setStringProperty(existingUser, propertyName, (String) propertyValue);
                    }
                    break;
                case Constants.COUNTRY:
                    if (propertyValue instanceof Country) {
                        existingUser.setCountry((Country) propertyValue);
                    }
                    break;
                case Constants.TIMEZONE:
                    if (propertyValue instanceof Timezone) {
                        existingUser.setTimezone((Timezone) propertyValue);
                    }
                    break;
                case Constants.ROLES:
                    if (propertyValue instanceof Set) {
                        existingUser.getRoles().clear();
                        existingUser.getRoles().addAll((Set<Role>) propertyValue);
                    }
                    break;
                default:
            }
        }
    }

    private static void setStringProperty(User existingUser, String propertyName, String propertyValue) {
        if (propertyValue != null && !Objects.equals(getPropertyValue(existingUser, propertyName), propertyValue)) {
            setPropertyValue(existingUser, propertyName, propertyValue);
        }
    }

    private static String getPropertyValue(User existingUser, String propertyName) {
        switch (propertyName) {
            case Constants.USERNAME:
                return existingUser.getUsername();
            case Constants.PASSWORD:
                return existingUser.getPassword();
            case Constants.FIRST_NAME:
                return existingUser.getFirstName();
            case Constants.MIDDLE_NAME:
                return existingUser.getMiddleName();
            case Constants.LAST_NAME:
                return existingUser.getLastname();
            case Constants.COMPANY_NAME:
                return existingUser.getCompanyName();
            case Constants.COMPANY_EMAIL:
                return existingUser.getCompanyEmail();
            case Constants.COUNTRY_CODE:
                return existingUser.getCountryCode();
            case Constants.PHONE:
                return existingUser.getPhone();
            case Constants.ADDRESS:
                return existingUser.getAddress();
            case Constants.WEBSITE:
                return existingUser.getWebsite();
            default:
                return null;
        }
    }

    private static void setPropertyValue(User existingUser, String propertyName, String propertyValue) {
        switch (propertyName) {
            case Constants.USERNAME:
                existingUser.setUsername(propertyValue);
                break;
            case Constants.PASSWORD:
                existingUser.setPassword(propertyValue);
                break;
            case Constants.FIRST_NAME:
                existingUser.setFirstName(propertyValue);
                break;
            case Constants.MIDDLE_NAME:
                existingUser.setMiddleName(propertyValue);
                break;
            case Constants.LAST_NAME:
                existingUser.setLastname(propertyValue);
                break;
            case Constants.COMPANY_NAME:
                existingUser.setCompanyName(propertyValue);
                break;
            case Constants.COMPANY_EMAIL:
                existingUser.setCompanyEmail(propertyValue);
                break;
            case Constants.COUNTRY_CODE:
                existingUser.setCountryCode(propertyValue);
                break;
            case Constants.PHONE:
                existingUser.setPhone(propertyValue);
                break;
            case Constants.ADDRESS:
                existingUser.setAddress(propertyValue);
                break;
            case Constants.WEBSITE:
                existingUser.setWebsite(propertyValue);
                break;
            default:
        }
    }

    /**
     * This method is used to update the properties of an existing user object with the properties of another user object.
     *
     * @param updatedUser  The user that needs to be updated or modified.
     * @param existingUser The existing user that needs to be updated with the new information.
     */
    public void setExistingUser(User updatedUser, User existingUser) {
        Map<String, Object> propertiesToUpdate = preparePropertyMap(updatedUser);
        mapObjects(existingUser, propertiesToUpdate);
        existingUser.setUpdatedAt(new Date());
    }

    /**
     * This method prepares a map of properties to update from the updatedUser object.
     *
     * @param updatedUser The updated user object containing new property values.
     * @return A map of property names to their updated values.
     */
    private Map<String, Object> preparePropertyMap(User updatedUser) {
        Map<String, Object> propertiesToUpdate = new HashMap<>();
        propertiesToUpdate.put(Constants.USERNAME, updatedUser.getUsername());
        propertiesToUpdate.put(Constants.PASSWORD, updatedUser.getPassword());
        propertiesToUpdate.put(Constants.FIRST_NAME, updatedUser.getFirstName());
        propertiesToUpdate.put(Constants.MIDDLE_NAME, updatedUser.getMiddleName());
        propertiesToUpdate.put(Constants.LAST_NAME, updatedUser.getLastname());
        propertiesToUpdate.put(Constants.COMPANY_NAME, updatedUser.getCompanyName());
        propertiesToUpdate.put(Constants.COMPANY_EMAIL, updatedUser.getCompanyEmail());
        propertiesToUpdate.put(Constants.COUNTRY_CODE, updatedUser.getCountryCode());
        propertiesToUpdate.put(Constants.PHONE, updatedUser.getPhone());
        propertiesToUpdate.put(Constants.ADDRESS, updatedUser.getAddress());
        propertiesToUpdate.put(Constants.WEBSITE, updatedUser.getWebsite());
        propertiesToUpdate.put(Constants.COUNTRY, updatedUser.getCountry());
        propertiesToUpdate.put(Constants.TIMEZONE, updatedUser.getTimezone());
        return propertiesToUpdate;
    }
}
