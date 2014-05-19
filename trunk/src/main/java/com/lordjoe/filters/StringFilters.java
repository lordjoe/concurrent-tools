package com.lordjoe.filters;


import org.xml.sax.*;

import javax.annotation.*;

/**
 * com.lordjoe.filters.StringFilters
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class StringFilters {

    public static final String TAG = "StringFilter";

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
            }
         };
    }

    public static ITypedFilter<String> getStartsWithFilter(final String ext) {
        return new AbstractStringTypedFilter() {
            @Override
            public String passes(@Nonnull String testObject) {
                if (!testObject.startsWith(ext))
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
    public static class StringFilterSaxHandler extends AbstractFilterCollectionSaxHandler<String>  {

        private ITypedFilter enclosed;

        public StringFilterSaxHandler(FilterCollectionSaxHandler parent) {
            super(TAG, parent);
          }



        @Override
        public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
            super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
            String value;
            value = attributes.getValue("startsWith");
            if (value != null) {
                String name = attributes.getValue("name");
                setElementObject(getStartsWithFilter(name));
                return;
            }
            value = attributes.getValue("endsWith");
            if (value != null) {
                String name = attributes.getValue("name");
                setElementObject(getEndsWithFilter(name));
                return;
            }
            throw new UnsupportedOperationException("Fix This"); // ToDo
        }



    }
}
