package com.example.ku.collection.utils;

import android.os.Environment;

import java.io.File;

/**
 * @author Ronan.zhuang
 * @date 8/5/16
 */

public class Constants {

    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static final String TEST_PATH = SD_PATH + File.separator + "ku_test";

    public static final String TEST_FILE = TEST_PATH + File.separator + "test.txt";
}
