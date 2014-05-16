package com.lordjoe.filters;

import com.lordjoe.utilities.*;

import javax.annotation.*;
import java.util.*;

/**
 * com.lordjoe.filters.AndTypedFilter
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class AndTypedFilter<T> extends AbstractTypedFilter<T> {
    private final List<ITypedFilter<T>> clauses = new ArrayList<ITypedFilter<T>>();

    public AndTypedFilter(Class<? extends T> applicableType) {
        super(applicableType);
    }

    public void addFilter(ITypedFilter added) {
        clauses.add(added); // todo test proper type
    }

    /**
     * return 0 if it passes the filter otherwise return null
     *
     * @param testObject
     * @return as above
     */
    @Override
    public T passes(@Nonnull T testObject) {
        for (ITypedFilter<T> clause : clauses) {
            if(clause.passes(testObject) == null)
                return null; // failed
        }
        return testObject; // passed
    }
}
