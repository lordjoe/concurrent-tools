package com.lordjoe.filters;

import org.systemsbiology.sax.*;

/**
 * com.lordjoe.filters.AbstractFilterCollectionSaxHandler
 *
 * @author Steve Lewis
 * @date 5/17/14
 */
public abstract class AbstractFilterCollectionSaxHandler<T> extends AbstractElementSaxHandler<ITypedFilter<T>> {
    private final FilterCollectionSaxHandler parentCollection;

    public AbstractFilterCollectionSaxHandler(String tag,FilterCollectionSaxHandler parentHandler,IElementHandler parent) {
        super(tag, parent);
        parentCollection = parentHandler;
    }

    public AbstractFilterCollectionSaxHandler(String tag,FilterCollectionSaxHandler parentHandler) {
        this(tag, parentHandler, null);
      }

    public FilterCollectionSaxHandler getParentCollection() {
        return parentCollection;
    } 
    /**
       * finish handling and set up the enclosed object
       * Usually called when the end tag is seen
       */
      @Override
      public void finishProcessing() {
         }

}
