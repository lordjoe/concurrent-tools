package uk.ac.ebi.pride.hadooprunner;

import java.util.*;

/**
 * uk.ac.ebi.pride.hadooprunner.InMemoryMapReduceRunner
 * User: Steve
 * Date: 8/5/2014
 */
public abstract class AbstractInMemoryMapReduceRunner< K extends IHadoopKey,D> implements IMapReduceRunner<K,D> {
    private final Map<String, List<D>> keysToClusters = new HashMap<String, List<D>>();
    private final List<D> inputClusters = new ArrayList<D>();
    private final List<D> foundClusters = new ArrayList<D>();


    /**
     * equivalent of write in hadoop
     *
     * @param key
     * @param value
     */
    @Override public void saveKeyValue(final K key, final D value) {
         String s = key.asKeyString();
         if(keysToClusters.containsKey(s))  {
             keysToClusters.get(s).add(value);
         }
        else {
             List<D> added = new ArrayList<D>();
             added.add(value);
             keysToClusters.put(s,added);
         }
    }

    /**
     * main map step
     *   use identity map
     * @param key
     * @param value
     */
    @Override public void mapKeyValues(final K key, final D value) {
        saveKeyValue( key,  value);
    }

    /**
     * main reduce step
     *
     * @param key
     * @param values
     */
      public abstract void handleKeyValues(final K key, final Iterator<D> values);

    /**
     * any operations to setup
     *
     * @param data - any data wo be passed in
     *             implementation specific
     */
    @Override public void setupMapReduce(final Object... data) {

    }

    /**
     * any operations to close
     *
     * @param data - any data wo be passed in
     */
    @Override public void shutdownMapReduce(final Object... data) {

    }
}
