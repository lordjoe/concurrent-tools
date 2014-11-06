package org.systemsbiology.remotecontrol;

import com.jcraft.jsch.*;
import org.systemsbiology.hadoop.*;
import org.systemsbiology.hadoopgenerated.*;

import java.io.*;

/**
 * org.systemsbiology.remotecontrol.RemoteSession
 * User: steven
 * Date: Jun 2, 2010
 */
public class RemoteSession implements UserInfo {
    public static final RemoteSession[] EMPTY_ARRAY = {};

    private final JSch m_JSCH = new JSch();
    private final String m_Host;
    private final String m_User;
    private final String m_Password;
    private int m_Port = 22;
    private Session m_Session;
    //    private FTPChannel m_FTPChannel;
    private boolean m_Connected;

    public RemoteSession(final String pHost, final String pUser, final String pPassword) {
        m_Host = pHost;
        m_User = pUser;
        m_Password = pPassword;
        try {
            m_Session = m_JSCH.getSession(m_User, m_Host, m_Port);
            m_Session.setUserInfo(this);
            m_Session.setConfig("StrictHostKeyChecking", "no");
        } catch (JSchException e) {
            throw new RuntimeException(e);

        }
    }


    public JSch getJSCH() {
        return m_JSCH;
    }


//    public synchronized FTPChannel getFTPChannel() {
//        if (m_FTPChannel == null) {
//            m_FTPChannel = new FTPChannel(this);
//        }
//        return m_FTPChannel;
//    }

    public String getHost() {
        return m_Host;
    }

    public String getUser() {
        return m_User;
    }

    public int getPort() {
        return m_Port;
    }

    public Session getSession() {
        return m_Session;
    }

    public boolean isConnected() {
        return m_Connected;
    }

    public void setConnected(final boolean pConnected) {
        if (m_Connected == pConnected)
            return;
        if (pConnected) {
            try {
                m_Session.connect();
            } catch (JSchException e) {
                throw new RuntimeException(e);
            }
            m_Connected = true;
        } else {
            m_Session.disconnect();
            m_Connected = false;

        }
        m_Connected = pConnected;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return m_Password;
    }

    @Override
    public boolean promptPassword(final String message) {
        return getPassword() != null;
    }

    @Override
    public boolean promptPassphrase(final String message) {
        return false;
    }

    @Override
    public boolean promptYesNo(final String message) {
        return false;
    }

    @Override
    public void showMessage(final String message) {
        System.out.println(message);
    }

    public static boolean runNShot(final IHadoopController pHc) {
        final String defaultPath = RemoteUtilities.getDefaultPath();
        //   pHc.guaranteeFilesOnHDFS(new File("E:/data/Moby"), "/user/slewis/moby", "/user/slewis/moby");
        IHadoopJob job = HadoopJob.buildJob(
                NShotTest.class,
                RemoteUtilities.getDefaultPath() + "/moby",     // data on hdfs
                defaultPath + "/jobs",      // jar location
                RemoteUtilities.getDefaultPath()             // output location - will have outputN added
        );


        return pHc.runJob(job);
    }

    private static boolean runWordCount(final IHadoopController pHc) {
        //   pHc.guaranteeFilesOnHDFS(new File("E:/data/Moby"), "/user/slewis/moby", "/user/slewis/moby");
        final String defaultPath = RemoteUtilities.getDefaultPath();
        String inputDirectory = defaultPath + "/books";
        IHadoopJob job = HadoopJob.buildJob(
         //      CapitalWordCount.class,     // not n IJobRunner
                CapitalWordCountRunner.class,
                  inputDirectory,     // data on hdfs
                defaultPath + "/jobs",      // jar location
                defaultPath + "/output"             // output location - will have outputN added

        );


        return pHc.runJob(job);
    }

    private static boolean runSubstringCount(final IHadoopController pHc) {
        //   pHc.guaranteeFilesOnHDFS(new File("E:/data/Moby"), "/user/slewis/moby", "/user/slewis/moby");
        String temporaryDirectory = pHc.getTemporaryDirectory();
        final String defaultPath = RemoteUtilities.getDefaultPath();
        IHadoopJob job = HadoopJob.buildJob(
                SubstringCount.class,
                "/user/howdah" + "/BigText.txt",     // data on hdfs
                defaultPath + "/jobs",      // jar location
                temporaryDirectory           // output location - will have outputN added

        );


        return pHc.runJob(job);
    }
//
//    private static boolean runStatisticalWordCount(final IHadoopController pHc) {
//     //   pHc.guaranteeFilesOnHDFS(new File("E:/data/Moby"), "/user/slewis/moby", "/user/slewis/moby");
//        IHadoopJob job = HadoopJob.buildJob(
//                HowdahStatisticalWordCount.class,
//                RemoteUtilities.getDefaultPath() + "/moby",     // data on hdfs
//                "/users/slewis/jobs",      // jar location
//                RemoteUtilities.getDefaultPath() ,             // output location - will have outputN added
//                "-D org.systemsbiology.configfile=" + RemoteUtilities.getDefaultPath() +  "/config/HowdahStatisticalWordCount.config"
//
//        );
//
//
//        return pHc.runJob(job);
//    }

    private static boolean runProbes(final IHadoopController pHc) {
        //  pHc.guaranteeFilesOnHDFS(new File("E:/data/probes"), "/home/training/probes", "/home/training/hdfsprobes");
        IHadoopJob job = HadoopJob.buildJob(
                HadoopProbe.class,
                RemoteUtilities.getDefaultPath() + "/hdfsprobes",     // data on hdfs
                "/user/howdah/jobs",      // jar location
                RemoteUtilities.getDefaultPath(),            // output location - will have outputN added
                "-D org.systemsbiology.configfile=" + RemoteUtilities.getDefaultPath() + "/config/HowdahStatisticalWordCount.config"

        );


        return pHc.runJob(job);
    }

//
//    private static boolean runShakesphear(final IHadoopController pHc) {
//        //   pHc.guaranteeFiles(new File("E:/Code/config"), "/user/howdah/config");
//        //   pHc.guaranteeFiles(new File("E:/Code/YeastReports"), "/user/howdah/YeastReports");
//        //   pHc.guaranteeFilesOnHDFS(new File("E:/Code/YeastData"), "/user/howdah/small_yeast", "/user/howdah/small_yeast");
//        //      pHc.guaranteeFilesOnHDFS(new File("E:/Code/YeastReports"), "/user/howdah/config", "/user/howdah/YeastReports");
//
//
//        IHadoopJob job = HadoopJob.buildJob(org.systemsbiology.couldera.training.index.LineIndexer.class,
//                "/user/training/input",  // data on hdfs
//                RemoteUtilities.getDefaultPath() + "/jobs",          // jar location
//                "/user/training/shak"               // output location - will have outputN added
//
//
//        );
//
//
//        return pHc.runJob(job);
//    }
//

    /*
  private static void runYeastHowdahProcess(IHadoopController launcher) {
        Class<PartitioningHowdahTask> mainClass = PartitioningHowdahTask.class;
        String jobName = mainClass.getSimpleName();

         IHadoopJob job = HadoopJob.buildJob(
                mainClass,
                "/user/howdah/YeastData",     // data on hdfs
             //   "/user/howdah/YeastSmallData",     // data on hdfs
              //  "YeastSmallData",     // data on hdfs
                 "jobs",      // jar location
                "yeast_output2",             // output location - will have outputN added
                  "-D",
                "org.systemsbiology.configfile=/user/howdah/config/HowdahBreaks.config",     // Config file
                "-D",
                "org.systemsbiology.reportfile=/user/howdah/yeastreportlarge.xml"  // report file
        );
        launcher.runJob(job);
    }


  private static void runHumanHowdahProcess(IHadoopController launcher) {
        Class<PartitioningHowdahTask> mainClass = PartitioningHowdahTask.class;
        String jobName = mainClass.getSimpleName();

         IHadoopJob job = HadoopJob.buildJob(
                mainClass,
                  "/user/www/TCGA-AA-3681-01A-01W-0900-09_IlluminaGA-DNASeq_exome.sorted.sam" ,
              //   "/user/howdah/NA19239Small",     // data on hdfs
             //   "/user/howdah/YeastSmallData",     // data on hdfs
              //  "YeastSmallData",     // data on hdfs
                  "jobs",      // jar location
                "/user/howdah/human_output",             // output location - will have outputN added
                  "-D",
                "org.systemsbiology.configfile=config/HowdahHumanBreaks.config",     // Config file
                "-D",
                "org.systemsbiology.reportfile=/user/howdah/NA19239Small.xml"  // report file
        );
        launcher.runJob(job);
    }

    */


    private static void runNShotTest(final IHadoopController pHc) {
        Class<NShotTest> mainClass = NShotTest.class;
        String jobName = mainClass.getSimpleName();

        String outDir = RemoteUtilities.getDefaultPath() + "/NShot";
        String jarLocation = "jobs";
        IHadoopJob job = HadoopJob.buildJob(
                mainClass,
                "FeeFie.txt",     // data on hdfs
                jarLocation,      // jar location
                outDir             // output location - will have outputN added

        );

        // make a small jar file
        File jarFile = HadoopDeployer.makeClassOnlyHadoopJar(jarLocation + "/NShot.jar", "org.systemsbiology.hadoopgenerated", "org.systemsbiology.hadoop");
        String jarString = jarFile.toString().replace("\\", "/");
        job.setJarFile(jarString);


        pHc.runJob(job);
    }

    private static void runHadoopTest(final IHadoopController pHc, String[] args) {
        Class<HadoopTest> mainClass = HadoopTest.class;
        String jobName = mainClass.getSimpleName();


        String temporaryDirectory = pHc.getTemporaryDirectory();
        String jarLocation = "jobs";
        IHadoopJob job = HadoopJob.buildJob(
                mainClass,
                "FeeFie.txt",     // data on hdfs
                jarLocation,      // jar location
                temporaryDirectory,             // output location - will have outputN added
                args

        );

        // make a small jar file
        //      File jarFile = HadoopDeployer.makeClassOnlyHadoopJar(jarLocation + "/HadoopTest.jar", "org.systemsbiology.hadoopgenerated" );
        //     String jarString = jarFile.toString().replace("\\", "/");
        //      job.setJarFile(jarString);


        pHc.runJob(job);
    }


//    public static void main(String[] args) throws Exception {
//        final String user = RemoteUtilities.getUser(); // "training";  //
//        final String password = RemoteUtilities.getPassword(); // "training";  //
//        final String host = RemoteUtilities.getHost(); // "192.168.244.128"; // "hadoop1";
//
//        if (HadoopMajorVersion.CURRENT_VERSION == HadoopMajorVersion.Version0)
//            throw new IllegalStateException("Version 1 is required for this code");
//
//        UserGroupInformation ugi = HDFWithNameAccessor.getCurrentUserGroup();
//        UserGroupInformation current = UserGroupInformation.getCurrentUser();
//
//        final RemoteSession rs = new RemoteSession(host, user, password);
//        rs.setConnected(true);
//
//        ugi.doAs(new PrivilegedExceptionAction<Void>() {
//
//
//            final RemoteHadoopController hc = new RemoteHadoopController(rs);
//
//
//            public Void run() throws Exception {
//                UserGroupInformation current = UserGroupInformation.getCurrentUser();
//                //   String path = RemoteUtilities.guaranteeClassPath(hc,"/user/howdah/lib");
//                // File test = new File("AverageWordLength.txt");
//                //  hc.copyDirectoryToHDFS(test,"/user/training/avg.txt");
//                //   runShakesphear(hc);
//                //      runYeastBreaks(hc);
//                // runSimGenerator(hc);
//                // runClouderaYeastBreaks(hc);
//                //    runStatisticalWordCount(hc);
//                //  runMotifLocator(hc);
//                //  runSubstringCount(hc);
//                runWordCount(hc);
//                //   runNShotTest(hc);
//                // runHadoopTest(hc,args);
//                // runProbes(hc);
//                //  runYeastHowdahProcess(hc);
//                //  runHumanHowdahProcess(hc);
//                return null;
//
//            }
//        });
//        rs.setConnected(false);
//        System.err.println("Done");
//
//    }

    public static void main(String[] args) throws Exception {

        RemoteUtilities.readResourceProperties();

        final String user = RemoteUtilities.getUser(); // "training";  //
        final String password = RemoteUtilities.getPassword(); // "training";  //
        final String host = RemoteUtilities.getHost(); // "192.168.244.128"; // "hadoop1";
        RemoteUtilities.setDefaultPath("/user/slewis/ebi");
        final String defaultPath = RemoteUtilities.getDefaultPath();

        if (HadoopMajorVersion.CURRENT_VERSION != HadoopMajorVersion.Version0)
            throw new IllegalStateException("Version 0 is required for this code");
        final RemoteSession rs = new RemoteSession(host, user, password);
        rs.setConnected(true);


        final RemoteHadoopController hc = new RemoteHadoopController(rs);

        HadoopJob.setHadoopCommand("/hadoop/local-install/bin/hadoop");
        /*
           This expects nothing and does not read the input
         */
//       runNShot(hc);

//     /*
//           This expects to run from a directory containing a subdirectory moby
//           and assumes that the same structure exists on hdfs
//         */
       runWordCount(hc);


        rs.setConnected(false);
        System.err.println("Done");

    }

}
