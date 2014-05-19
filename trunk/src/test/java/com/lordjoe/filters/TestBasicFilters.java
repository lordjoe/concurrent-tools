package com.lordjoe.filters;

import org.junit.*;

import java.io.*;
import java.util.*;

/**
 * com.lordjoe.filters.TestBasicFilters
 *
 * @author Steve Lewis
 * @date 16/05/2014
 */
public class TestBasicFilters {

    public static final String[] IDENTIFIED_STRINGS = {
            "fee", "fie", "foe", "fum",
            "FEE", "FIE", "FOE", "FUM"
    };

    public static final String[] FILE_NAMES = {
            "fee.zip", "fie.mgf", "foe.mgf", "fum.mgf",
            "FEE.zip", "FIE.zip", "FOE.mgf", "FUM.exe"
    };


    @Test
    public void testNot() throws Exception {
        Set<String> passesAnswer = new HashSet<String>();
        final ITypedFilter<String> caps_filter = StringFilters.CAPS_FILTER;
        final ITypedFilter<String> not_caps_filter = new NotTypeFilter<String>(caps_filter);

        for (int i = 0; i < IDENTIFIED_STRINGS.length; i++) {
            String str = IDENTIFIED_STRINGS[i];
            final String passes = caps_filter.passes(str);
            if (passes != null) {
                passesAnswer.add(passes);
                Assert.assertNull(not_caps_filter.passes(passes));
            } else {
                Assert.assertNotNull(not_caps_filter.passes(str));
                Assert.assertEquals(str, not_caps_filter.passes(str));
            }
        }

        Assert.assertEquals(4, passesAnswer.size());
    }


    @Test
    public void testCaps() throws Exception {
        Set<String> passesAnswer = new HashSet<String>();
        final ITypedFilter<String> caps_filter = StringFilters.CAPS_FILTER;
        for (int i = 0; i < IDENTIFIED_STRINGS.length; i++) {
            String str = IDENTIFIED_STRINGS[i];
            final String passes = caps_filter.passes(str);
            if (passes != null)
                passesAnswer.add(passes);
        }

        Assert.assertEquals(4, passesAnswer.size());
    }

    @Test
    public void testEndsWith() throws Exception {
        Set<String> passesAnswer = new HashSet<String>();
        final ITypedFilter<String> caps_filter = StringFilters.getEndsWithFilter(".mgf");
        for (int i = 0; i < FILE_NAMES.length; i++) {
            String str = FILE_NAMES[i];
            final String passes = caps_filter.passes(str);
            if (passes != null)
                passesAnswer.add(passes);
        }

        Assert.assertEquals(4, passesAnswer.size());
    }

    @Test
    public void testFileFilter() throws Exception {
        File home = new File(System.getProperty("user.home"));

        // home is is directory
        final ITypedFilter<File> directory_filter = FileFilters.DIRECTORY_FILTER;
        final ITypedFilter<File> file_filter = FileFilters.FILE_FILTER;
        File dir = directory_filter.passes(home);
        Assert.assertEquals(home, dir);

        // is not a file
        final File flx = file_filter.passes(home);
        Assert.assertNull(flx);

        final File[] files = home.listFiles();
        List<File> testdirs = new ArrayList<File>();
        List<File> testhomeFiles = new ArrayList<File>();
        List<File> dirs = new ArrayList<File>();
        List<File> homeFiles = new ArrayList<File>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                dir = directory_filter.passes(file);
                if (dir != null)
                    dirs.add(file);
                else
                    homeFiles.add(file);

                if (file.isDirectory())
                    testdirs.add(file);
                else
                    testhomeFiles.add(file);
            }
        }

        Assert.assertArrayEquals(testdirs.toArray(), dirs.toArray());
        Assert.assertArrayEquals(testhomeFiles.toArray(), homeFiles.toArray());
    }
}
