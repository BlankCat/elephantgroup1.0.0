package com.elephantgroup.blog.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @Title: SDCardCtrl.java
 * @Package com.elephantgroup.blog.util
 * @Description: SDCard control class
 */
public class SDCardCtrl {

    /**
     * The log cat Tag
     */
    public static final String TAG = "SDCheck";

    /**
     * ROOTPATH
     */
    public static String ROOTPATH = "/.elephantGroup";

    /**
     * UPLOADPATH
     */
    public static String UPLOADPATH = "/UploadPath";

    /**
     * DOWNLOADPATH
     */
    public static String DOWNLOAD = "/ElephantGroup/DownloadPath";


    /**
     * QRCODEPATH
     */
    public static String QRCODEPATH = "/ElephantGroup/QrcodePath";

    /**
     * ERRORLOGPATH
     */
    public static String ERRORLOGPATH = "/ErrorLogPath";

    /**
     * DYNAMICPATH
     */
    public static String DYNAMICPATH = "/DynamicPath";


    /**
     * File
     */
    public static String FILE = "/File";

    /**
     * TEMPATH
     */
    public static String TEMPATH = "/TempPath";

    /**
     * ChatPATH
     */
    public static String CHATPATH = "/Chat";

    /**
     * IMAGEPATH
     */
    public static String IMAGEPATH = "/Images";

    public static String VIDEO = "/Video";

    /**
     * FACE
     */
    public static String FACE = "/Face";

    /**
     * CHAT_FILE
     */
    public static String CHAT_FILE_PATH = "/File";

    @SuppressWarnings("unused")
    private static Context context;

    /**
     * @param context
     */
    @SuppressWarnings("static-access")
    public SDCardCtrl(Context context) {
        this.context = context;
    }


    public static String getErrorLogPath() {
        return ERRORLOGPATH;
    }


    /**
     * @return UPLOADPATH
     */
    public static String getUploadPath() {
        return UPLOADPATH;
    }

    public static String getQrcodePath() {
        return QRCODEPATH;
    }

    /**
     * @return TEMPATH
     */
    public static String getTempPath() {
        return TEMPATH;
    }

    /**
     * @return DYNAMICPATH
     */
    public static String getDynamicPath() {
        return DYNAMICPATH;
    }

    /**
     * @return FILE
     */
    public static String getFilePath() {
        return FILE;
    }

    /**
     * @return NEXTDYNAMICPATH
     */
    public static String getImagePath() {
        return IMAGEPATH;
    }

    public static String getVideoPath() {
        return VIDEO;
    }

    public static String getFacePath() {
        return FACE;
    }


    /**
     * @return Is or not exist SD card
     */
    public static boolean sdCardIsExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * <Build data file for this application >
     */
    public static void initPath() {

        String ROOT;

        if (sdCardIsExist()) {
            ROOT = Environment.getExternalStorageDirectory().getPath();
        } else {
            ROOT = "/mnt/sdcard";
        }

        if (ROOTPATH.equals("/.elephantGroup")) {
            ROOTPATH = ROOT + ROOTPATH;
            ERRORLOGPATH = ROOTPATH + ERRORLOGPATH;
            UPLOADPATH = ROOTPATH + UPLOADPATH;
            QRCODEPATH = ROOT + QRCODEPATH;
            DOWNLOAD = ROOT + DOWNLOAD;
            TEMPATH = ROOTPATH + TEMPATH;
            DYNAMICPATH = ROOTPATH + DYNAMICPATH;
            FILE = ROOTPATH + FILE;
            CHATPATH = ROOTPATH + CHATPATH;
            IMAGEPATH = ROOTPATH + IMAGEPATH;
            VIDEO = ROOTPATH + VIDEO;
            FACE = ROOTPATH + FACE;
            CHAT_FILE_PATH = CHATPATH + CHAT_FILE_PATH;
        }
        SDFileUtils.getInstance().createDir(ROOTPATH);
        SDFileUtils.getInstance().createDir(ERRORLOGPATH);
        SDFileUtils.getInstance().createDir(UPLOADPATH);
        SDFileUtils.getInstance().createDir(QRCODEPATH);
        SDFileUtils.getInstance().createDir(TEMPATH);
        SDFileUtils.getInstance().createDir(DYNAMICPATH);
        SDFileUtils.getInstance().createDir(CHATPATH);
        SDFileUtils.getInstance().createDir(IMAGEPATH);
        SDFileUtils.getInstance().createDir(VIDEO);
        SDFileUtils.getInstance().createDir(FACE);
        SDFileUtils.getInstance().createDir(CHAT_FILE_PATH);
    }

}
