package com.lordjoe.filters;

import javax.annotation.*;
import java.util.*;

/**
 * com.lordjoe.filters.OrTypedFilter
 *   takes a series o clauses and passes if any are true
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class OrTypedFilter<T> extends AbstractCompositeFilter<T> {

    public OrTypedFilter( ) {
     }

    /**
     * return 0 if it passes the filter otherwise return null
     *   NOTE This is NOT thread same with adding clauses
     * @param testObject
     * @return as above
     */
    @Override
    public T passes(@Nonnull T testObject) {
        final List<ITypedFilter<T>> clauses = internalGetClauses();
        if(clauses.isEmpty())
            return testObject; // by convention empty filters succeed
        for (ITypedFilter<T> clause : clauses) {
            if(clause.passes(testObject) != null)
                return testObject; // passes
        }
        return null; // failed
    }
}
