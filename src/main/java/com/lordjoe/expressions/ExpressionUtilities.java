package com.lordjoe.expressions;

import com.lordjoe.filters.*;
import org.codehaus.janino.*;

import javax.annotation.*;
import java.lang.reflect.*;

/**
 * com.lordjoe.expressions.ExpressionUtilities
 *  allow filters (and later other code to read and evaluate string expressions
 *  at runtime
 *  @See ExpressionTests in the test package for samples of usage
 * @author Steve Lewis
 * @date 19/05/2014
 */

@SuppressWarnings("unchecked")
public class ExpressionUtilities {

    /**
     *
     * @param expression
     * @param className
      * @return  filter based on that class and expression
     */
    public   static @Nonnull <T> ITypedFilter<T> makeEvaluatedFilter(
           @Nonnull String expression,
           @Nonnull String className
    ) {
        try {
            Class<T> type = ( Class<T>)Class.forName(className);
            return new ExpressionTypedFilter<T>(type, expression);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }

    }

    /**
     * implementing class for an expression based filter -
     * NOTE the parameter is hard coded to x
     * expression might be x.size < 5
     * @param <T>
     */
    protected static class ExpressionTypedFilter<T> extends AbstractTypedFilter<T> implements IEvaluator<Boolean,T> {

        private final ExpressionEvaluator evaluator;

        private ExpressionTypedFilter(Class<? extends T> applicableType, String expression) {
            super(applicableType);
            try {
                evaluator = new ExpressionEvaluator(
                        expression,                  // boolean expression
                        Boolean.class,               // expressionType
                        new String[]{"x"},           // parameterNames
                        new Class[]{applicableType}  // parameterTypes
                );
            } catch (CompileException e) {
                throw new UnsupportedOperationException("cannot compile " + e.getMessage() + expression + " for class " + applicableType,e);
            } catch (Parser.ParseException e) {
                throw new UnsupportedOperationException("cannot parse " + e.getMessage() + expression + " for class " + applicableType,e);
                } catch (Scanner.ScanException e) {
                throw new UnsupportedOperationException("cannot scan " + e.getMessage() + expression + " for class " + applicableType,e);
               }

        }

        /**
         * @param inp
         * @return
         */
        @Override
        public Boolean evaluate(T inp) {
            try {
                 Object[] args = {inp};
                 return (Boolean)evaluator.evaluate(args);

            } catch (InvocationTargetException e) {
                 throw new UnsupportedOperationException(e);
             }
         }

        /**
         * return 0 if it passes the filter otherwise return null
         * @param testObject
         * @return as above
         */
        @Override
        public T passes(@Nonnull T testObject) {
            if(evaluate(testObject))
                return testObject;
            else
                return null;
        }
    }
}
