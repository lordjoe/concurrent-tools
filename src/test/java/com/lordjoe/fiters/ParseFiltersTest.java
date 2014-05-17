package com.lordjoe.fiters;

import com.lordjoe.filters.*;
import org.junit.*;
import org.systemsbiology.xml.*;

import java.io.*;

/**
 * com.lordjoe.fiters.ParseFiltersTest
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class ParseFiltersTest {
    public static final String FILTER_1 = "<Filters>\n" +
            "<Not>\n" +
            "<StringFilter startsWith=\"F\" />\n" +
            "</Not>\n" +
            "<FileFilter extension=\"mgf2\" />\n" +
            "<FileFilter extension=\"log\" />\n" +
            "<!--\n" +
             "<Filter applicableType=\"uk.ac.ebi.pride.spectracluster.spectrum.ISpectrum\" charge=\"2\" />\n" +
            "-->\n" +
            "</Filters>\n";

    @Test
    public void testParseFilters() {
        FilterCollectionSaxHandler handler = new FilterCollectionSaxHandler();
        final TypedFilterCollection typedFilterCollection = XMLUtilities.parseXMLString(FILTER_1, handler);
    }

}
