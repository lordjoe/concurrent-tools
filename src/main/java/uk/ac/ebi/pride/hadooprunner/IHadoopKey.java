package uk.ac.ebi.pride.hadooprunner;

/**
 * uk.ac.ebi.pride.hadooprunner.IHadoopKey
 * User: Steve
 * Date: 8/5/2014
 */
public interface IHadoopKey {

    /**
     * convert to a string which will sort and bin well
     * @return
     */
    public String asKeyString();



}
