package org.systemsbiology.hadoop;


import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.*;

import java.io.*;
import java.util.*;

/**
 *  org.systemsbiology.hadoop.CapitalWordCountRunner
 *  For test this is a copy of  CapitalWordCount
 */
public class CapitalWordCountRunner extends ConfiguredJobRunner implements IJobRunner {

    public static final  String TEST_PROPERTY = "org.systemsbiology.status";
 
    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        /**
         * Called once at the beginning of the task.
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            Configuration conf  = context.getConfiguration();

            String isBar = conf.get(TEST_PROPERTY);
            Counter counter = context.getCounter("test", "bar");
            if("foobar".equals(isBar))
                counter.increment(1);
        }

        public void map(LongWritable key, Text value, Context context
        ) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String s = itr.nextToken().toUpperCase();
                s = dropNonLetters(s);
                if (s.length() > 0) {
                    word.set(s);
                    context.write(word, one);
                }
            }
        }
    }

    public static String dropNonLetters(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c))
                sb.append(c);
        }

        return sb.toString();
    }

    public static class IntSumReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(new Text(key.toString()), result);
        }
    }


    public  int runJob(Configuration conf,String[] args) throws Exception {

        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            System.err.println(arg);
        }
           String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();

        Job job;
        if(conf instanceof JobConf)  {
            job = new Job((JobConf)conf  );
        }
        else {
            job = new Job(conf  );
        }
           job.setJobName("word count");
        conf = job.getConfiguration(); // NOTE JOB Copies the configuraton
        job.setJarByClass(CapitalWordCountRunner.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        job.setInputFormatClass(TextInputFormat.class);

        // added Slewis
        job.setNumReduceTasks(2); // cheaper on amazon HadoopUtilities.DEFAULT_REDUCE_TASKS);
    //    job.setPartitionerClass(MyPartitioner.class);

        if(otherArgs.length > 1)    {
            FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        }

        // you must pass the output directory as the last argument
        String athString = otherArgs[otherArgs.length - 1];
        //noinspection UnnecessaryLocalVariable,UnusedDeclaration,UnusedAssignment
        File out = new File(athString);
//        if (out.exists()) {
//            FileUtilities.expungeDirectory(out);
//            out.delete();
//        }

        Path outputDir = new Path(athString);

        FileSystem fileSystem = outputDir.getFileSystem(conf);
        HadoopUtilities.expunge(outputDir,fileSystem);    // make sure thia does not exist
        FileOutputFormat.setOutputPath(job, outputDir);


        boolean ans = job.waitForCompletion(true);
        //noinspection UnnecessaryLocalVariable,UnusedDeclaration,UnusedAssignment
        int ret = ans ? 0 : 1;
        return ret;
    }

    /**
     * Execute the command with the given arguments.
     *
     * @param args command specific arguments.
     * @return exit code.
     * @throws Exception
     */
    @Override
    public int run(final String[] args) throws Exception {
        Configuration conf = getConf();
        if(conf == null)
            conf = HDFSAccessor.getSharedConfiguration();
        //      conf.set(BamHadoopUtilities.CONF_KEY,"config/MotifLocator.config");
        return runJob(conf, args);
    }


    public static void main(String[] args) throws Exception {
       ToolRunner.run(new CapitalWordCountRunner(), args);
    }
}