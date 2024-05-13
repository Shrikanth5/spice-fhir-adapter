package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * The {@code User} class represents an entity that stores information about users,
 * including their username, password, personal details, company information, country,
 * role, timezone, and security-related data.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FieldConstants.USER)
public class User extends BaseEntity implements UserDetails {

    @Column(name = FieldConstants.USERNAME)
    private String username;

    @ColumnTransformer(forColumn = Constants.PASSWORD,
            read = Constants.PGP_DECRYPT + Constants.SINGLE_QUOTE + Constants.FHIR_SALT_NAME + Constants.SINGLE_QUOTE + Constants.CLOSE_BRACKET,
            write = Constants.PGP_ENCRYPT + Constants.SINGLE_QUOTE + Constants.FHIR_SALT_NAME + Constants.SINGLE_QUOTE + Constants.CLOSE_BRACKET)
    @Column(name = FieldConstants.PASSWORD, columnDefinition = Constants.BYTEA)
    private String password;

    @Column(name = FieldConstants.FIRSTNAME)
    private String firstName;

    @Column(name = FieldConstants.MIDDLE_NAME)
    private String middleName;

    @Column(name = FieldConstants.LASTNAME)
    private String lastname;

    @Column(name = FieldConstants.COMPANY_NAME)
    private String companyName;

    @Column(name = FieldConstants.COMPANY_EMAIL)
    private String companyEmail;

    @Column(name = FieldConstants.COUNTRY_CODE)
    private String countryCode;

    @Column(name = FieldConstants.PHONE)
    private String phone;

    @Column(name = FieldConstants.ADDRESS)
    private String address;

    @Column(name = FieldConstants.WEBSITE)
    private String website;

    @ManyToOne
    @JoinColumn(name = FieldConstants.COUNTRY)
    private Country country;

    @ManyToOne
    @JoinColumn(name = FieldConstants.TIMEZONE)
    private Timezone timezone;

    @Column(name = FieldConstants.FORGET_PASSWORD)
    private String forgetPassword;

    @Column(name = FieldConstants.FORGET_PASSWORD_TIME)
    private Date forgetPasswordTime;

    @Column(name = FieldConstants.FORGET_PASSWORD_COUNT)
    private Integer forgetPasswordCount = 0;

    @Column(name = FieldConstants.INVALID_LOGIN_ATTEMPTS)
    private Integer invalidLoginAttempts = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = FieldConstants.TABLE_USER_ROLE, joinColumns = {
            @JoinColumn(name = FieldConstants.USER_ID)}, inverseJoinColumns = {
            @JoinColumn(name = FieldConstants.ROLE_ID)})
    private Set<Role> roles = new HashSet<>();

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return new LinkedHashSet<>(roles);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}