package com.jyt.project.jytapp.activity;

import android.app.Application;
import android.content.Context;

import com.jyt.project.jytapp.util.CommonConstants;
import com.jyt.project.jytapp.util.log;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by pch on 2017/3/22.
 */

public class MyApplication extends Application{
    private static Context context;
    private String logFileName = null;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        configLog(CommonConstants.SDF.format(new Date()));//配置日志
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .build(); // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public static Context getContext(){
        return context;
    }

    private LogConfigurator logConfigurator = new LogConfigurator();
    public void configLog(String logFileNamePrefix) {
        if (null == logFileName || !logFileNamePrefix.equals(logFileName)) {
            logFileName = logFileNamePrefix;
            logConfigurator.setUseFileAppender(true);
            logConfigurator.setFileName(CommonConstants.LOG_PATH + File.separator+ logFileNamePrefix + ".log");
            logConfigurator.setRootLevel(Level.DEBUG);
            logConfigurator.setLevel("org.apache", Level.ERROR);
            logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
            logConfigurator.setMaxFileSize(1024 * 1024 * 20);
            logConfigurator.setImmediateFlush(true);
            logConfigurator.configure();
            Logger.getLogger(log.TAG).info("[MyApp]: 当前的日志文件名字:"+logConfigurator.getFileName());
        }
    }
}
