package com.lordjoe.filters;

import javax.annotation.*;
import java.io.*;

/**
 * com.lordjoe.filters.FileFilters
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class FileFilters {

    /**
     * filter of type file
     */
    protected static abstract class AbstractFileTypedFilter extends AbstractTypedFilter<File> {
        public AbstractFileTypedFilter() {
            super(File.class);
        }
    }

    /**
     * exists and is   directory
     */
    public static ITypedFilter<File> DIRECTORY_FILTER = new AbstractFileTypedFilter() {
        /**
         * return 0 if it passes the filter otherwise return null
         *
         * @param testObject
         * @return as above
         */
        @Override
        public File passes(@Nonnull File testObject) {
            if (!testObject.exists())
                return null;
            if (testObject.isDirectory())
                return testObject;
            return null;
        }

        ;
    };


    /**
     * exists and is file  not directory
     */
    public static ITypedFilter<File> FILE_FILTER = new AbstractFileTypedFilter() {
        /**
         * return 0 if it passes the filter otherwise return null
         *
         * @param testObject
         * @return as above
         */
        @Override
        public File passes(@Nonnull File testObject) {
            if (!testObject.exists())
                return null;
            if (!testObject.isDirectory())
                return testObject;
            return null;
        }

        ;
    };

    public static ITypedFilter<File> getHasExtensionFilter(final String ext) {
        return new AbstractFileTypedFilter() {
            /**
             * return 0 if it passes the filter otherwise return null
             *
             * @param testObject
             * @return as above
             */
            @Override
            public File passes(@Nonnull File testObject) {
                if (!testObject.exists())
                    return null;
                if (!testObject.getName().endsWith(ext))
                    return testObject;
                return null;
            };
        };
    }

}
