package com.lordjoe.filters;

import org.junit.*;

/**
 * com.lordjoe.filters.ParseFiltersTest
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
            "<Or>\n" +
            "<FileFilter extension=\"mgf2\" />\n" +
            "<FileFilter extension=\"log\" />\n" +
            "</Or>\n" +
            "<ExpressionFilter applicableType=\"com.lordjoe.expressions.ExpressionTests$TestRectangle\" " +
            "       expression=\"x.getArea() &lt; 20\" />\n" +
            "<And>\n" +
            "<StringFilter startsWith=\"F\" />\n" +
            "<StringFilter endsWith=\"e\" />\n" +
            "</And>\n" +

            "<!-- will not run without spectrum class\n" +
            "<Filter applicableType=\"uk.ac.ebi.pride.spectracluster.spectrum.ISpectrum\" charge=\"2\" />\n" +
            "-->\n" +
            "</Filters>\n";

    @Test
    public void testParseFilters() {

        System.out.println(FILTER_1);
         final TypedFilterCollection typedFilterCollection = TypedFilterCollection.parse(FILTER_1 );


    }

}
