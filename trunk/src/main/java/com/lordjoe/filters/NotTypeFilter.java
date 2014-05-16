package com.lordjoe.filters;

import javax.annotation.*;

/**
 * com.lordjoe.filters.NotTypeFilter
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class NotTypeFilter<T> extends AbstractTypedFilter {
    private final ITypedFilter wrappedFilter;

    public NotTypeFilter(@Nonnull ITypedFilter wrappedFilter) {
        super(wrappedFilter.getApplicableType());
        this.wrappedFilter = wrappedFilter;
    }

    /**
     * return 0 if it passes the filter otherwise return null
     *
     * @param testObject
     * @return as above
     */
    @Override
    public Object passes(@Nonnull Object testObject) {
        if(!wrappedFilter.isApplicable(testObject))
            return testObject; // todo or should this return null???
        if (wrappedFilter.passes(testObject) != null)
            return testObject; // passes
        return null;  // fails
    }
}
