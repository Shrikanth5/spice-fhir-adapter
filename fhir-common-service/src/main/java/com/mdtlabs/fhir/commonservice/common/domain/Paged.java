package com.mdtlabs.fhir.commonservice.common.domain;

import java.util.List;

/**
 * <p>
 * Paged Interface
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public interface Paged<T> {

    /**
     * <p>
     * Get list of generic object T.
     * </p>
     *
     * @return {@link List<T>} - list of entity
     */
    List<T> getList();

    /**
     * <p>
     * Total count
     * </p>
     *
     * @return - count as a long type
     */
    long getCount();

    /**
     * <p>
     * gets Generic object T
     * </p>
     *
     * @return Object T - entity object
     */
    T getObject();

}
