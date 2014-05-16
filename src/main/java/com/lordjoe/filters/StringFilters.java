package com.lordjoe.filters;

import javax.annotation.*;
import java.io.*;

/**
 * com.lordjoe.filters.StringFilters
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class StringFilters {

    /**
     * filter of type String
     */
    protected static abstract class AbstractStringTypedFilter extends AbstractTypedFilter<String> {
        public AbstractStringTypedFilter() {
            super(String.class);
        }
    }

    /**
     * exists and is   directory
     */
    public static ITypedFilter<String> CAPS_FILTER = new AbstractStringTypedFilter() {
             public String passes(@Nonnull String testObject) {
            if (testObject.toUpperCase().equals(testObject))
                return testObject;
                return null;
        }
     };

    /**
      * exists and is   directory
      */
     public static ITypedFilter<String> LOWER_FILTER = new AbstractStringTypedFilter() {
              public String passes(@Nonnull String testObject) {
             if (testObject.toLowerCase().equals(testObject))
                 return testObject;
                 return null;
         }
      };



    public static ITypedFilter<String> getEndsWithFilter(final String ext) {
        return new AbstractStringTypedFilter() {
              @Override
            public String passes(@Nonnull String testObject) {
                if (!testObject.endsWith(ext))
                    return null;
                 return testObject;
              };
        };
    }

}
