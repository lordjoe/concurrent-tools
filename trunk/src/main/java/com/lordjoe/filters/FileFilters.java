package com.lordjoe.filters;

import org.systemsbiology.sax.*;
import org.xml.sax.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

/**
 * com.lordjoe.filters.FileFilters
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class FileFilters {
    public static final String TAG = "FileFilter";

    /**
     * @param startDir existing file or directory
     * @param filters  filters to apply
     * @return all filts that pass
     */
    public static List<File> applyFilters(@Nonnull File startDir, @Nonnull TypedFilterCollection filters) {
        List<File> holder = new ArrayList<File>();

        internalApplyFilters(holder, startDir, filters);

        return holder;
    }

    /**
     * @param startDir existing file or directory
     * @param filters  filters to apply
     * @return all filts that pass
     */
    protected static void internalApplyFilters(@Nonnull List<File> holder, @Nonnull File file, @Nonnull TypedFilterCollection filters) {
        if (file.isDirectory()) {
            if(filters.passes(file) != null) {
                final File[] files = file.listFiles();
                if(files != null) {
                    for (int i = 0; i < files.length; i++) {
                        File file1 = files[i];
                        internalApplyFilters(holder, file1, filters);
                    }
                }
            }
        } else {
            if(filters.passes(file) != null) {
                holder.add(file);
            }
        }
    }

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
    };

    /**
     * return true of a file has an extension
     *
     * @param ext
     * @return
     */
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
                if (testObject.isDirectory())
                    return testObject;  // pass directory for recursion
                final String name = testObject.getName();
                if (!name.endsWith("." + ext))
                    return null;
                return testObject;
            }
        };
    }
    /**
        * return true of a file a length greater than maxlength
        *
        * @param length max allowed length
        * @return
        */
       public static ITypedFilter<File> getMinimumLength(final long length) {
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
                   if (!testObject.isFile())
                       return testObject;  // pass directory for recursion
                   if (testObject.length() > length)
                       return testObject;
                   return null;
               }
           };
       }

    /**
        * return true of a file a length less than maxlength
        *
        * @param length max allowed length
        * @return
        */
       public static ITypedFilter<File> getMaximumLength(final long length) {
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
                   if (!testObject.isFile())
                       return testObject;  // pass directory for recursion
                   if (testObject.length() < length)
                       return testObject;
                   return null;
               }
           };
       }

    /**
     *
     * @param minlength minimumAllowedLength
     * @param maxLength maxumimAllowedLength
     * @return a filter
     */
         public static ITypedFilter<File> getLimitedLength(final long minlength,final long maxLength) {
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
                     if (!testObject.isFile())
                         return testObject;  // pass directory for recursion
                     final long length = testObject.length();
                     if (length > maxLength)
                         return null;
                     if (length > minlength)
                          return null;
                       return testObject;
                 }
             };
         }


    /**
     * com.lordjoe.filters.FilterCollectionSaxHandler
     * reads xml document <Filters></Filters>
     *
     * @author Steve Lewis
     * @date 16/05/2014
     */
    public static class FileFilterSaxHandler extends AbstractFilterCollectionSaxHandler<File>   {


        public FileFilterSaxHandler(FilterCollectionSaxHandler parent) {
            super(TAG, parent);
        }


        @Override
        public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
            super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
            String value;
            value = attributes.getValue("extension");
            if (value != null) {
                 setElementObject(getHasExtensionFilter(value));
                return;
            }
            value = attributes.getValue("minimumLength");
            String otherValue = attributes.getValue("maximumLength");

             if (value != null) {
                 long length = parseLengthString(value);
                 // specify bit max and min length
                if(otherValue != null)  {
                    long minlength = parseLengthString(otherValue);
                    setElementObject(getLimitedLength(minlength, length));
                    return;
                }
               setElementObject(getMinimumLength(length));
                return;
            }
             if (otherValue != null) {
                long length = parseLengthString(otherValue);
                 setElementObject(getMaximumLength(length));
                return;
            }
            StringBuilder sb = new StringBuilder();
               for (int i = 0; i < attributes.getLength(); i++) {
                   sb.append(attributes.getQName(i) + "=\"" + attributes.getValue(i) + "\" ");

            }
            throw new UnsupportedOperationException("no file filters we understand " + sb);
        }

        @Override
        public void endElement(String elx, String localName, String el) throws SAXException {
            super.endElement(elx, localName, el);

        }

        protected static long parseLengthString(String value) {
            final int end = value.length() - 1;
            char c = value.charAt(end); // last char
            long factor = 1;
            switch (c)   {
                case 'k' :   // say 23k
                case 'K' :
                    factor = 1024;
                    value = value.substring(0,end);
                    break;
                case 'm' :
                case 'M' :
                    factor = 1024 * 1024;
                    value = value.substring(0,end);
                    break;
                case 'g' :
                case 'G' :
                    factor = 1024 * 1024 * 1024;
                    value = value.substring(0,end);
                    break;

            }
            return factor * Integer.parseInt(value);
        }

    }
}
