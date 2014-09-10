package uk.ac.ebi.pride.hadooprunner;

import java.util.*;

/**
 * uk.ac.ebi.pride.hadooprunner.IMapReduceRunner
 *
 * @author Steve Lewis
 * @date 5/14/14
 */
public interface IMapReduceRunner<K,D> {
    /**
     * equivalent of write in hadoop
      * @param key
     * @param value
     */
    public void saveKeyValue(K key, D value);

    /**
     * main reduce step
     * @param key
     * @param values
     */
    public void mapKeyValues(K key,D value);

    /**
     * main reduce step
     * @param key
     * @param values
     */
    public void handleKeyValues(K key,Iterator<D> values);

    /**
     * any operations to setup
     * @param data - any data wo be passed in
     *             implementation specific
     */
    public void setupMapReduce(Object... data);

    /**
     * any operations to close
     * @param data - any data wo be passed in
     */
    public void shutdownMapReduce(Object... data);


}
