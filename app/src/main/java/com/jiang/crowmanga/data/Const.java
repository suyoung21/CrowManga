package com.jiang.crowmanga.data;

import android.os.Environment;

import java.io.File;

public class Const {
    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "manga" + File.separator;

    public static final String CRASH_LOG_PATH = APP_PATH + "crashLog";
    /**
     * 崩溃日志文件地址
     */
    public static final String CRASH_LOG_FILE_PATH = CRASH_LOG_PATH + File.separator + "crash_log.txt";
}
