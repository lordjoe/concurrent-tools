package org.systemsbiology.remotecontrol;

import org.junit.*;

import java.io.*;

/**
 * org.systemsbiology.remotecontrol.FileSystemTests
 * User: Steve
 * Date: 9/16/13
 */
public class FileSystemTests {

    /**
     * make sure /tmp exists and has something in it!!!
     * @throws Exception
     */
    @Test
    public void testFileSize() throws Exception {
        File top = new File("/");
        String path = top.getAbsolutePath();
        LocalMachineFileSystem fs = new LocalMachineFileSystem(top);
        long len = HDFSUtilities.size("/tmp", fs);
        Assert.assertTrue(len > 0);

    }
}
