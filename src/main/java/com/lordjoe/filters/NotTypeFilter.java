package com.lordjoe.filters;

import javax.annotation.*;
import java.util.*;

/**
 * com.lordjoe.filters.NotTypeFilter
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class NotTypeFilter<T> extends AbstractCompositeFilter<T> {

    public NotTypeFilter(@Nonnull ITypedFilter wrappedFilter) {
        super( );
        this.addFilter(wrappedFilter);
    }

    @Override
    public void addFilter(ITypedFilter added) {
        final List<ITypedFilter<T>> clauses = internalGetClauses();
        if(!clauses.isEmpty())
            throw new IllegalStateException("not filters allow only one clause");
        super.addFilter(added);

    }

    /**
     * return 0 if it passes the filter otherwise return null
     *
     * @param testObject
     * @return as above
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object passes(@Nonnull Object testObject) {
        final List<ITypedFilter<T>> clauses = internalGetClauses();
        if(clauses.isEmpty())
            throw new IllegalStateException("not filters need one clause");
        ITypedFilter<T> wrappedFilter = clauses.get(0);
        if(!wrappedFilter.isApplicable(testObject))
            return testObject; // todo or should this return null???
        if (wrappedFilter.passes((T)testObject) == null)
            return testObject; // passes since wrapped filter fails
        return null;  // fails
    }
}
