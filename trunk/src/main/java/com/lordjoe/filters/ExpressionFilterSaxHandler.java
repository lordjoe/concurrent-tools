package com.lordjoe.filters;


import com.lordjoe.expressions.*;
import org.xml.sax.*;

import javax.annotation.*;

/**
 * com.lordjoe.filters.ExpressionFilterSaxHandler
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class ExpressionFilterSaxHandler extends AbstractFilterCollectionSaxHandler<Object> {

    public static final String TAG = "ExpressionFilter";


    public ExpressionFilterSaxHandler(FilterCollectionSaxHandler parent) {
        super(TAG, parent);
    }


    @Override
    public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
        String className = attributes.getValue("applicableType");
        String expression = attributes.getValue("expression");
        if(className == null )
            throw new IllegalStateException("ExpressionFilter requires applicableType attribute");
        if(expression == null )
              throw new IllegalStateException("ExpressionFilter requires expression attribute");
           setElementObject(ExpressionUtilities.makeEvaluatedFilter(expression, className));
        return;
    }


}
