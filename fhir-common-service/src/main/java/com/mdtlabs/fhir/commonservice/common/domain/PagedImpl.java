package com.mdtlabs.fhir.commonservice.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * Paged Implementation
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Getter
@Setter
public class PagedImpl<T> implements Paged<T> {

    /**
     * <p>
     * List of T
     * </p>
     */
    private List<T> list;

    /**
     * <p>
     * Object used Generically
     * </p>
     */
    private T object;

    /**
     * <p>
     * Total count
     * </p>
     */
    private long count;

    /**
     * <p>
     * It is used to create an instance of the `PagedImpl`
     * class without initializing any of its fields.
     * </p>
     */
    public PagedImpl() {
    }

    /**
     * <p>
     * It sets the `list` field of the `PagedImpl` object to the `List`
     * parameter and the `count` field to the `long` parameter.
     * </p>
     *
     * @param list  {@link List<T>} list param
     * @param count count param
     */
    public PagedImpl(List<T> list, long count) {
        this.list = list;
        this.count = count;
    }

    /**
     * <p>
     * Java constructor for PagedImpl class that sets object field to parameter value, count field to count parameter.
     * </p>
     *
     * @param object the object parameter is given
     * @param count  the count parameter is given
     */
    public PagedImpl(T object, long count) {
        this.object = object;
        this.count = count;
    }

    /**
     * <p>
     * Java code that defines a public method named getObject(), which returns the object field of the PagedImpl class.
     * This method is used when the Paged interface is implemented with a single object instead of a list.
     * </p>
     */
    @Override
    public T getObject() {
        return this.object;
    }

}
