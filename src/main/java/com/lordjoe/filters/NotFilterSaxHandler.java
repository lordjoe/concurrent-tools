package com.lordjoe.filters;

import org.systemsbiology.sax.*;
import org.xml.sax.*;

/**
 * com.lordjoe.filters.FilterCollectionSaxHandler
 * reads xml document <Filters></Filters>
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class NotFilterSaxHandler extends AbstractElementSaxHandler<NotTypeFilter> implements ITopLevelSaxHandler {
    public static final String TAG = "Not";

    private ITypedFilter enclosed;

    public NotFilterSaxHandler(DelegatingSaxHandler pParent) {
        super(TAG, pParent);
    }

    public NotFilterSaxHandler(IElementHandler parent) {
        super(TAG, parent);
    }


    @Override
    public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (!TAG.equals(qName)) {
            final FilterCollectionSaxHandler parent = (FilterCollectionSaxHandler) getParent();
            final AbstractElementSaxHandler handler = parent.getHandler(qName);
            handler.setParent(this);
            final DelegatingSaxHandler handler1 = getHandler();
            handler1.pushCurrentHandler(handler);
            handler.handleAttributes(uri, localName, qName, attributes);
            return;
        }
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void endElement(final String elx, final String localName, final String el) throws SAXException {
        if (!TAG.equals(el)) {
            ISaxHandler ch = getHandler().popCurrentHandler();
            enclosed = (ITypedFilter) ((AbstractElementSaxHandler) ch).getElementObject();
            return;
        }
        setElementObject(new NotTypeFilter(enclosed));

        super.endElement(elx, localName, el);    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * finish handling and set up the enclosed object
     * Usually called when the end tag is seen
     */
    @Override
    public void finishProcessing() {
//        setElementObject(new NotTypeFilter(enclosed));
    }
}
