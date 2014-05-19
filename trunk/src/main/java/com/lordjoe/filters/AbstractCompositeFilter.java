package com.lordjoe.filters;

import javax.annotation.*;
import java.util.*;

/**
 * com.lordjoe.filters.AbstractCompositeFilter
 *
 * @author Steve Lewis
 * @date 19/05/2014
 */
public abstract class AbstractCompositeFilter<T> extends AbstractTypedFilter<T> {
    private final List<ITypedFilter<T>> clauses = new ArrayList<ITypedFilter<T>>();
    private Class inferredType;

    protected AbstractCompositeFilter( ) {
        super(null);
      }

    public List<ITypedFilter<T>> internalGetClauses() {
        return clauses;
    }

    @SuppressWarnings("unchecked")
    public void addFilter(ITypedFilter added) {

        final Class applicableType = added.getApplicableType();
        if (inferredType == null) {
            inferredType = applicableType;
        } else {
            if (!inferredType.isAssignableFrom(applicableType))
                throw new IllegalArgumentException("bad filter type");
        }
        clauses.add(added);
    }

    /**
     * return 0 if it passes the filter otherwise return null
     *
     * @param testObject
     * @return as above
     */
    @Override
    public T passes(@Nonnull T testObject) {
         throw new UnsupportedOperationException("Fix This");
       }

    /**
     * return the class the filter applies to
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public Class<? extends T> getApplicableType() {
        if (inferredType != null)
            return inferredType;
        if(clauses.isEmpty())
             throw new IllegalStateException("no defined type");
        else
            return clauses.get(0).getApplicableType(); // first type

    }
}
