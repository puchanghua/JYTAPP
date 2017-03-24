package com.jyt.project.jytapp.util;

import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by pch on 2017/3/21.
 */

public class CommonConstants {
    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory()+ File.separator+"images"+File.separator;
    public static final String LOG_PATH = Environment.getExternalStorageDirectory().getPath()+ File.separator + "JYT" + File.separator + "Logs"+File.separator+"TTC";// 日志文件文件夹路径
    public static int LOG_INFO = 0;
    public static int LOG_DEBUG = 1;
    public static int LOG_WARNING = 2;
    public static int LOG_ERROR = 3;
    // 日志引擎 日期格式化的格式
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SD2 = new SimpleDateFormat("yyyyMMdd");
}
