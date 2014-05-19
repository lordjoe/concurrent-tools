package com.lordjoe.expressions;

import com.lordjoe.filters.*;
import org.junit.*;

/**
 * com.lordjoe.expressions.ExpressionTests
 *   These tests are an example of how to make a filter from an expression
 * @author Steve Lewis
 * @date 19/05/2014
 */
public class ExpressionTests {

    public static final String WIDTH_GT_10 = "x.getWidth() > 10";
    public static final String AREA_LT_20 = "x.getArea() < 20";
    public static final String CLASS_NAME = TestRectangle.class.getName();

    /**
     * make a couple of expression based filters and test
     */
    @Test
    public void testExpression() {
        ITypedFilter<TestRectangle> widthFilter = ExpressionUtilities.makeEvaluatedFilter(WIDTH_GT_10,CLASS_NAME);
        ITypedFilter<TestRectangle> areaFilter = ExpressionUtilities.makeEvaluatedFilter(AREA_LT_20,CLASS_NAME);

        Class tr= TestRectangle.class;
        String name = tr.getName();

        TestRectangle r1 = new TestRectangle(5,3);
        TestRectangle r2 = new TestRectangle(50,3);
        TestRectangle r3 = new TestRectangle(5,30);

        Assert.assertEquals(r1,areaFilter.passes(r1)); // r1 passes area test
        Assert.assertNull(areaFilter.passes(r2)); // r2 fails area test
        Assert.assertNull(areaFilter.passes(r3)); // r3 fails area test


        Assert.assertEquals(r2,widthFilter.passes(r2)); // r2 passes width test
        Assert.assertNull(widthFilter.passes(r1)); // r1 fails width test
        Assert.assertNull(widthFilter.passes(r3)); // r3 fails width test

    }

    public static class TestRectangle
    {
        private final int height;
        private final int width;

        public TestRectangle(int width, int height) {
            this.height = height;
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
        // used in expression tests
        @SuppressWarnings("UnusedDeclaration")
        public int getArea() {
              return getHeight() * getWidth();
          }
     }
}
