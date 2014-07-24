package com.lordjoe.hadoop;

import java.util.*;
/**
 * com.lordjoe.hadoop.ITextMapper
 *
 * @author Steve Lewis
 * @date 5/15/13
 */
public interface ITextMapper {
    public static ITextMapper[] EMPTY_ARRAY = {};
    /**
     * implementation to return the key and value as a TextKeyValue array
     */
    public static  ITextMapper IDENTITY_MAPPER = new ITextMapper() {
        @Override public TextKeyValue[] map(final String key, final String value, final Properties config) {
            TextKeyValue[] ret = new TextKeyValue[1];
            ret[0] = new TextKeyValue(key,value);
            return ret;
        }
    };

    public TextKeyValue[]  map(String key,String value,Properties config);
}
