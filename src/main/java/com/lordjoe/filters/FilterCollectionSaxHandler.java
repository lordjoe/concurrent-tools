package com.lordjoe.filters;

import org.systemsbiology.sax.*;
import org.systemsbiology.xml.*;
import org.xml.sax.*;

import java.util.*;

/**
 * com.lordjoe.filters.FilterCollectionSaxHandler
 * reads xml document <Filters></Filters>
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class FilterCollectionSaxHandler extends AbstractElementSaxHandler<TypedFilterCollection> implements ITopLevelSaxHandler {
    public static final String TAG = "Filters";

    private final Map<String, AbstractElementSaxHandler> handlers = new HashMap<String, AbstractElementSaxHandler>();

    public FilterCollectionSaxHandler() {
        super(TAG, (DelegatingSaxHandler) null);
        setElementObject(new TypedFilterCollection());
        addHardCodedHandlers();
    }

    public FilterCollectionSaxHandler(DelegatingSaxHandler pParent) {
        super(TAG, pParent);
        setElementObject(new TypedFilterCollection());
        addHardCodedHandlers();
    }

    public FilterCollectionSaxHandler(IElementHandler parent) {
        super(TAG, parent);
        setElementObject(new TypedFilterCollection());
        addHardCodedHandlers();
    }

    private void addHardCodedHandlers()
    {
        handlers.put(NotFilterSaxHandler.TAG,new NotFilterSaxHandler(this)) ;
    }

    public AbstractElementSaxHandler getHandler(String tag) {
        return handlers.get(tag);
    }

    @Override
    public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (!TAG.equals(qName)) {
            final AbstractElementSaxHandler handler = getHandler(qName);
            getHandler().pushCurrentHandler(handler);
            handler.handleAttributes(uri, localName, qName, attributes);
            return;
        }
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void endElement(final String elx, final String localName, final String el) throws SAXException {
        if (!TAG.equals(elx)) {
            ISaxHandler ch = getHandler().popCurrentHandler();
            ITypedFilter added = (ITypedFilter) ((AbstractElementSaxHandler) ch).getElementObject();
            return;
        }
        super.endElement(elx, localName, el);    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * finish handling and set up the enclosed object
     * Usually called when the end tag is seen
     */
    @Override
    public void finishProcessing() {

    }
}
