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
public class CompositeFilterSaxHandler extends AbstractFilterCollectionSaxHandler<NotTypeFilter>  {

    private final AbstractCompositeFilter enclosed;
    private final String tag;


    public CompositeFilterSaxHandler(String tag,FilterCollectionSaxHandler parent) {

        super(tag, parent,parent);
        this.tag = tag;
        if("And".equals(tag))  {
            enclosed = new AndTypedFilter();
            setElementObject(enclosed);
            return;
        }
        if("Or".equals(tag))  {
             enclosed = new OrTypedFilter();
            setElementObject(enclosed);
             return;
         }
        throw new IllegalArgumentException("only And and Or tags handled");
     }

    protected AbstractCompositeFilter getEnclosed() {
        return enclosed;
    }

    @Override
    public void handleAttributes(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        super.handleAttributes(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
        if (!tag.equals(qName)) {
            final FilterCollectionSaxHandler parent =   getParentCollection();
            final AbstractElementSaxHandler handler = parent.getHandler(qName);
            handler.setParent(null);   // allow reuse
            handler.setParent(this);
            final DelegatingSaxHandler handler1 = getHandler();
            handler1.pushCurrentHandler(handler);
            handler.handleAttributes(uri, localName, qName, attributes);
            return;
        }
        super.startElement(uri, localName, qName, attributes);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    public void endElement(final String elx, final String localName, final String el) throws SAXException {
        if (!tag.equals(el)) {
            ISaxHandler ch = getHandler().popCurrentHandler();
            ITypedFilter added = (ITypedFilter) ((AbstractElementSaxHandler) ch).getElementObject();
            enclosed.addFilter(added);
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
//        setElementObject(new NotTypeFilter(enclosed));
    }
}
