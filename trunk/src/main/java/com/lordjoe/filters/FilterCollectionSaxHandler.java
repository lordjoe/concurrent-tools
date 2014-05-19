package com.lordjoe.filters;

import org.systemsbiology.sax.*;
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

    /**
     * known tag handlers - others may be added at runtime
     */
    private void addHardCodedHandlers() {
        final Map<String, AbstractElementSaxHandler> handlersC = TypedFilterCollection.getHandlers();
        for (String s : handlersC.keySet()) {
            final AbstractElementSaxHandler sh = handlersC.get(s);
            if (sh instanceof AbstractFilterCollectionSaxHandler) {
                AbstractFilterCollectionSaxHandler ab = (AbstractFilterCollectionSaxHandler) sh;
                ab.setParentCollection(null);  // allow reseting
                ab.setParentCollection(this);
              }
            handlers.put(s,sh);
        }
    }

    public AbstractElementSaxHandler getHandler(String tag) {
        final AbstractElementSaxHandler saxHandler = handlers.get(tag);
        if (saxHandler == null)
            throw new IllegalArgumentException("cannot file handler " + tag);
        final DelegatingSaxHandler handler = getHandler();
        saxHandler.setHandler(handler);
        return saxHandler;
    }

    @Override
    public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (!TAG.equals(qName)) {
            final AbstractElementSaxHandler handler = getHandler(qName);
            handler.setParent(this);
            getHandler().pushCurrentHandler(handler);
            handler.handleAttributes(uri, localName, qName, attributes);
            return;
        }
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void endElement(final String elx, final String localName, final String el) throws SAXException {
        if (!TAG.equals(el)) {
            final DelegatingSaxHandler handler = getHandler();
            ISaxHandler ch = handler.popCurrentHandler();
            final Object elementObject = ((AbstractElementSaxHandler) ch).getElementObject();
            ITypedFilter added = (ITypedFilter) elementObject;
            getElementObject().addFilter(added);
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
