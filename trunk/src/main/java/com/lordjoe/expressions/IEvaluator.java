package com.lordjoe.expressions;

/**
 * com.lordjoe.expressions.IEvaluator
 *
 * @author Steve Lewis
 * @date 19/05/2014
 */
public interface IEvaluator<Out,In> {

    /**
     *
     * @param inp
     * @return
     */
      public Out evaluate(In inp);
}
