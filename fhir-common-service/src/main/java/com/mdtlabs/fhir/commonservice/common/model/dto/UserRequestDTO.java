package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This is a DTO class for user entity.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class UserRequestDTO {

    private String username;

    private String password;

    private String firstName;

    private String middleName;

    private String lastname;

    private String companyName;

    private String companyEmail;

    private String countryCode;

    private String phone;

    private String address;

    private String website;

    private Country country;

    private Timezone timezone;

    private Integer forgetPasswordCount = 0;

    private Integer invalidLoginAttempts = 0;

    private Set<Role> roles = new HashSet<>();

}