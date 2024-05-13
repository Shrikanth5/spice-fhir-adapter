package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * The {@code Role} class represents an entity that stores information about roles,
 * including their name and level.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@Entity
@Table(name = FieldConstants.ROLE)
public class Role extends BaseEntity implements Serializable, GrantedAuthority {

    @Column(name = FieldConstants.NAME)
    private String name;

    @Column(name = FieldConstants.LEVEL)
    private Integer level;

    @Transient
    private String authority;

    public Role(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Role() {
    }

    /**
     * <p>
     * The function returns the name of the authority.
     * </p>
     *
     * @return The name of the authority is returned
     */
    public String getAuthority() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Role role))
            return false;
        return !Objects.isNull(this.getId()) && this.getId().equals(role.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(authority, level, name);
        return result;
    }
}
