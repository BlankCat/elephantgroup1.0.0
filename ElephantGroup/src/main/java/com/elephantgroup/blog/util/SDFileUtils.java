package com.elephantgroup.blog.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * SD卡辅助类
 *
 * @author ck 1124
 */
public class SDFileUtils {
    private static SDFileUtils sdFile = null;

    public static String basePath = SDCardCtrl.ROOTPATH;

    private static String jsonPathString = "";

    private boolean isDownloadingProps;

    public static synchronized SDFileUtils getInstance() {
        if (sdFile == null) {
            sdFile = new SDFileUtils();
        }
        return sdFile;
    }

    private SDFileUtils() {
        createDir(getSDPath() + basePath);
    }

    public String getLocalAvatarPath() {
        return getSDCardPath() + basePath;
    }

    public String getjsonPath() {
        return jsonPathString;
    }

    /**
     * 检查SD卡是否插好,并返回SD卡路径
     */
    public boolean SDCardIsOk() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD路径
     *
     * @return /sdcard
     */
    public String getSDPath() {
        // 判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.getPath();
        }
        return "/mnt/sdcard";
    }

    /**
     * 创建文件夹
     *
     * @param dirName
     */
    public void createDir(String dirName) {
        File destDir = new File(dirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public String getSDCardPath() {
        return Environment.getExternalStorageDirectory() + "/";
    }

    public void mkDir(File file) {

        if (file == null) return;

        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            mkDir(file.getParentFile());
            file.mkdir();
        }

    }

    /**
     * 在SD卡上创建文件
     *
     * @param fileName
     * @return
     */
    public File creatSDFile(String fileName) {
        File file = new File(getSDCardPath() + fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     * @return
     */
    public File createSDDir(String dirName) {
        File dir = new File(getSDCardPath() + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 检查SD卡上的文件夹是否存在(相对路径)
     *
     * @param fileName
     * @return
     */
    public boolean isFileExit(String fileName) {
        File file = new File(getSDCardPath() + fileName);
        return file.exists();
    }

    /**
     * 检查SD卡上的文件夹是否存在(绝对路径)
     *
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /**
     * 判断文件是否存在
     *
     * @param name 文件名
     * @return
     */
    public boolean fileExist(String name) {
        File f = new File(getSDCardPath() + name);
        return f.exists();
    }

    /**
     * 将InputStream里面的数据写入到SD卡中
     *
     * @param path     文件夹路径
     * @param fileName 文件名
     * @param input    输入流
     * @return
     */
    public File writeFileToSDCard(String path, String fileName,InputStream input) {
        File file = null;
        OutputStream ops = null;
        try {
            createSDDir(path);
            file = creatSDFile(path + fileName);
            ops = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((input.read(buffer) != -1)) {
                ops.write(buffer);
            }
            ops.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ops) {
                    ops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 输入流转化成图片
     * @param is          输入流
     * @param imgPathTemp 文件夹路径
     * @param fileName    文件名
     * @return
     */
    public File inputToFile(InputStream is, String imgPathTemp, String fileName) {
        createDir(imgPathTemp);
        File file = new File(imgPathTemp, fileName);// 保存文件
        try {
            if (!file.exists() && !file.isDirectory()) {
                // 可以在这里通过文件名来判断，是否本地有此图片
                FileOutputStream fos = new FileOutputStream(file);
                int data = is.read();
                while (data != -1) {
                    fos.write(data);
                    data = is.read();
                }
                fos.close();
                is.close();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据byte数组，生成文件
     */
    public void getFile(byte[] bfile, String imgPathTemp, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        createDir(imgPathTemp);
        File file = new File(imgPathTemp, fileName);// 保存文件
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 数据转化成文件
     *
     * @param datas       数据源
     * @param imgPathTemp 文件夹路径
     * @param fileName    文件名
     * @return
     */
    public File ByteToFile(byte[] datas, String imgPathTemp, String fileName) {
        createDir(imgPathTemp);
        File file = new File(imgPathTemp, fileName);// 保存文件
        try {
            if (!file.exists() && !file.isDirectory()) {
                // 可以在这里通过文件名来判断，是否本地有此图片
                FileOutputStream fos = new FileOutputStream(file);
                ByteArrayInputStream bais = new ByteArrayInputStream(datas);
                int data = bais.read();
                while (data != -1) {
                    fos.write(data);
                    data = bais.read();
                }
                fos.close();
                bais.close();
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写文件到SD卡
     * @param fileName 文件名
     * @param message  文件内容
     * @author ck
     * @date 2013-1-10 下午04:35:32
     */
    public void writeFileSdcard(String fileName, String message) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * @param path
     */
    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    // 获得系统可用内存信息
    public String getSystemAvaialbeMemorySize(Context ct) {
        // 获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager) ct.getSystemService(Context.ACTIVITY_SERVICE);
        // 获得MemoryInfo对象
        MemoryInfo memoryInfo = new MemoryInfo();
        // 获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;
        // 字符类型转换
        String availMemStr = formateFileSize(memSize, ct);
        return availMemStr;
    }

    // 调用系统函数，字符串转换 long -String KB/MB
    private String formateFileSize(long size, Context ct) {
        return Formatter.formatFileSize(ct, size);
    }

    /**
     * 获取内存卡容量大小
     * @param path
     * @return
     */
    public long getRoomSize(String path) {
        File file = new File(path);
        return file.length();
    }

    /**
     * 删除文件夹及其里面的子文件夹及文件
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }


    /**
     * 复制单个文件
     *
     * @param oldPath String  原文件路径  如：c:/fqf.txt
     * @param newPath String  复制后路径  如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <Save the App crash info to sdcard>
     */
    public static String saveCrashInfoToFile(String excepMsg) {
        if (TextUtils.isEmpty(excepMsg)) {
            return "";
        }
        String errorlog = SDCardCtrl.getErrorLogPath();
        FileWriter fw = null;
        PrintWriter pw = null;
        File logFile = null;
        try {
            StringBuilder logSb = new StringBuilder();
            logSb.append("crashlog");
            logSb.append("(");
            logSb.append(Utils.getSimpDate());
            logSb.append(")");
            logSb.append(".txt");
            logFile = new File(errorlog, logSb.toString());
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.write(excepMsg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                }
            }
        }
        return logFile == null ? "" : logFile.getAbsolutePath();
    }
}
