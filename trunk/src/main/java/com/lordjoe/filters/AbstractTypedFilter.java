package com.lordjoe.filters;

import javax.annotation.*;

/**
 * com.lordjoe.filters.AbstractTypedFilter
 *  base class for most typed filters
 * @author Steve Lewis
 * @date 16/05/2014
 */
public abstract class AbstractTypedFilter<T> implements ITypedFilter<T> {

    private final Class<? extends T> applicableType;

    protected AbstractTypedFilter(Class<? extends T> applicableType) {
        this.applicableType = applicableType;
    }

    /**
     * return the class the filter applies to
     * @return
     */
    public @Nonnull Class<? extends T> getApplicableType() {
        return applicableType;
    }

    /**
     * default implementation getApplicableType().isInstance(o)
     *
     * @param o
     * @return
     */
    @Override
    public boolean isApplicable(Object o) {
         return getApplicableType().isInstance(o);
    }

    /**
     * return 0 if it passes the filter otherwise return null
     *
     * @param testObject
     * @return as above
     */
    @Override
    public abstract T passes(@Nonnull T testObject);
}
