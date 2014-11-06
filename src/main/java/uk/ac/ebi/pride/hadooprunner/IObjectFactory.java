package uk.ac.ebi.pride.hadooprunner;

import javax.annotation.*;

/**
 * uk.ac.ebi.pride.hadooprunner.IObjectFactory
 * User: Steve
 * Date: 8/5/2014
 */
public interface IObjectFactory<T> {

    /**
     * create an object from a String
     * @param s
     * @param <T>  type returned - could be an interface
     * @return
     */
    public @Nonnull <T> T buildObject(@Nonnull String s);

}
