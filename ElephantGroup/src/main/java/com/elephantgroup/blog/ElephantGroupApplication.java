package com.elephantgroup.blog;

import android.app.Application;

import com.elephantgroup.blog.util.CrashHandler;
import com.elephantgroup.blog.util.SDCardCtrl;

/**
 * Application
 * Created on 2016/11/14.
 */
public class ElephantGroupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //创建文件夹
        SDCardCtrl.initPath();

        //错误日志收集
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
