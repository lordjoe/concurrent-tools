package com.lordjoe.filters;

import javax.annotation.*;
import java.util.*;

/**
 * com.lordjoe.filters.TypedFilterCollection
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class TypedFilterCollection {
    private final Set<ITypedFilter> allFilters = new HashSet<ITypedFilter>();

    public TypedFilterCollection() {
    }

    public void addFilter(@Nonnull ITypedFilter added) {
        allFilters.add(added);
    }

    /**
     *  rturn all filters which can operate on a specific type
     * @param cls
     * @return
     */
    public @Nonnull Collection<ITypedFilter> getApplicableFilters(@Nonnull Class cls)  {
        List<ITypedFilter> holder = new ArrayList<ITypedFilter>();
        for (ITypedFilter f : allFilters) {
             if(f.getApplicableType().isAssignableFrom(cls))
                 holder.add(f);
        }
         return holder;
    }
}
