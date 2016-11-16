package com.elephantgroup.blog;

import android.app.Application;

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
    }
}
