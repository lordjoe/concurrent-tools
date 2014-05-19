package com.lordjoe.filters;


import javax.annotation.*;


/**
 * com.lordjoe.filters.AndTypedFilter
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class AndTypedFilter<T> extends AbstractCompositeFilter<T> {

    public AndTypedFilter( ) {
        super( );
    }


    /**
     * return 0 if it passes the filter otherwise return null
     * @param testObject
     * @return as above
     */
    @Override
    public T passes(@Nonnull T testObject) {
        for (ITypedFilter<T> clause : internalGetClauses()) {
            if(clause.passes(testObject) == null)
                return null; // failed
        }
        return testObject; // passed
    }
}
