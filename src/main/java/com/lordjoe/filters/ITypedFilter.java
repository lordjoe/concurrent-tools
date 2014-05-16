package com.lordjoe.filters;

import javax.annotation.*;

/**
 * com.lordjoe.filters.ITypedFilter
 * interface for a filter operating on a class
 * most implementations start with
 * @author Steve Lewis
 * @date 16/05/2014
 */
public interface ITypedFilter<T> {

    /**
     * return the class the filter applies to
     * @return
     */
    public @Nonnull Class<? extends T> getApplicableType();

    /**
     * default implementation getApplicableType().isInstance(o)
     * @param o
     * @return
     */
    public boolean isApplicable(Object o);

    /**
     * return 0 if it passes the filter otherwise return null
     * @param testObject
     * @return as above
     */
    public T passes(@Nonnull T testObject);
}
