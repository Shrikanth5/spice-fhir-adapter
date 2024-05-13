package com.mdtlabs.fhir.commonservice.common.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * This is a DTO class for user entity.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class AuthUserDTO {
    private Long id;
    private String username;
    @JsonIgnore
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
    private Integer forgetPasswordCount;
    private Integer invalidLoginAttempts;
    private Boolean isSuperUser = false;
    private Set<Role> roles = new HashSet<>();
    private long currentDate;

    /**
     * <p>
     * This function returns a set of RoleDTO objects by mapping each element of the roles list to a
     * RoleDTO object using ModelMapper.
     * </p>
     *
     * @return A set of RoleDTO objects is being returned. The method uses Java 8 stream and map
     * functions to convert each Role object in the roles list to a RoleDTO object using ModelMapper,
     * and then collects them into a new set using Collectors.toSet().
     */
    public Set<RoleDTO> getRoles() {
        return roles.stream().map(role -> new ModelMapper().map(role, RoleDTO.class)).collect(Collectors.toSet());
    }

    /**
     * <p>
     * This function maps a timezone object to a TimezoneDTO object using ModelMapper.
     * </p>
     *
     * @return {@link TimezoneDTO} A TimezoneDTO object is being returned
     */
    public TimezoneDTO getTimezone() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(timezone, TimezoneDTO.class);
    }

    /**
     * <p>
     * This function returns a CountryDTO object mapped from a country object using ModelMapper.
     * </p>
     *
     * @return {@link CountryDTO} A CountryDTO object is being returned
     */
    public CountryDTO getCountry() {
        if (Objects.isNull(country)) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(country, CountryDTO.class);
    }
}
