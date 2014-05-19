package com.lordjoe.filters;

import org.systemsbiology.sax.*;
import org.systemsbiology.xml.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

/**
 * com.lordjoe.filters.TypedFilterCollection
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class TypedFilterCollection implements ITypedFilter<Object> {

    private static final Map<String, AbstractElementSaxHandler> handlers = new HashMap<String, AbstractElementSaxHandler>();
    private static boolean handlersInitialized;

    private static void guaranteeHandlersInitialized() {
        if (handlersInitialized)
            return;
        handlers.put(NotFilterSaxHandler.TAG, new NotFilterSaxHandler(null));
        handlers.put("And", new CompositeFilterSaxHandler("And", null));
        handlers.put("Or", new CompositeFilterSaxHandler("Or", null));
        handlers.put(StringFilters.TAG, new StringFilters.StringFilterSaxHandler(null));
        handlers.put(FileFilters.TAG, new FileFilters.FileFilterSaxHandler(null));
        handlers.put("ExpressionFilter", new ExpressionFilterSaxHandler(null));
        handlersInitialized = true;

    }

    public static void  registerHandler(String tag,AbstractElementSaxHandler handler)
    {
        handlers.put(tag, handler);

    }

    public static Map<String, AbstractElementSaxHandler> getHandlers() {
        return handlers;
    }


    public static
    @Nonnull
    TypedFilterCollection parse(@Nonnull File is) {
        guaranteeHandlersInitialized();
        FilterCollectionSaxHandler handler = new FilterCollectionSaxHandler();
        final TypedFilterCollection typedFilterCollection = XMLUtilities.parseFile(is, handler);
        return typedFilterCollection;
    }

    public static
    @Nonnull
    TypedFilterCollection parse(@Nonnull InputStream is) {
        guaranteeHandlersInitialized();
        FilterCollectionSaxHandler handler = new FilterCollectionSaxHandler();
        final TypedFilterCollection typedFilterCollection = XMLUtilities.parseFile(is, handler, null);
        return typedFilterCollection;
    }

    public static
    @Nonnull
    TypedFilterCollection parse(@Nonnull String is) {
        guaranteeHandlersInitialized();
        FilterCollectionSaxHandler handler = new FilterCollectionSaxHandler();
        final TypedFilterCollection typedFilterCollection = XMLUtilities.parseXMLString(is, handler);
        return typedFilterCollection;
    }

    private final Set<ITypedFilter> allFilters = new HashSet<ITypedFilter>();

    public TypedFilterCollection() {
    }

    public void addFilter(@Nonnull ITypedFilter added) {
        allFilters.add(added);
    }

    protected Set<ITypedFilter> internalGetAllFilters() {
        return allFilters;
    }

    /**
     * return all filters which can operate on a specific type
     *
     * @param cls
     * @return
     */
    @SuppressWarnings("UnusedDeclaration")
    public
    @Nonnull
    Collection<ITypedFilter> getApplicableFilters(@Nonnull Class cls) {
        List<ITypedFilter> holder = new ArrayList<ITypedFilter>();
        for (ITypedFilter f : allFilters) {
            if (f.getApplicableType().isAssignableFrom(cls))
                holder.add(f);
        }
        return holder;
    }

    /**
     * return the class the filter applies to
     *
     * @return
     */
    @Nonnull
    @Override
    public Class<?> getApplicableType() {
        return Object.class;
    }

    /**
     * default implementation getApplicableType().isInstance(o)
     *
     * @param o
     * @return
     */
    @Override
    public boolean isApplicable(Object o) {
        return true;
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
        for (ITypedFilter filter : internalGetAllFilters()) {
            if (filter.isApplicable(testObject))
                if (filter.passes(testObject) == null)
                    return null;
        }
        return testObject;
    }

    /**
     * like passes but does a better job of typing
     *
     * @param test
     * @param <T>  data type
     * @return the input of the filter passes
     */
    @SuppressWarnings("UnusedDeclaration")
    public <T> T passesType(T test) {
        if (passes(test) != null)
            return test;
        return null;
    }
}
