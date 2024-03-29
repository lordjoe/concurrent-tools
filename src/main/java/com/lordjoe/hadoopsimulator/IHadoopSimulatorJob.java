package com.lordjoe.hadoopsimulator;

import com.lordjoe.hadoop.*;

import java.util.*;

/**
 * com.lordjoe.hadoopsimulator.IHadoopJob
 *
 * @author Steve Lewis
 * @date 5/15/13
 */
public interface IHadoopSimulatorJob {

    public List<TextKeyValue>  runJob(List<TextKeyValue> input,Properties config);
}
