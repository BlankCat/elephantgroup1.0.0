package com.elephantgroup.blog.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elephantgroup.blog.R;
import com.elephantgroup.blog.ui.webwiew.WebViewUI;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Utils {

    /**
     * 发送广播
     * @param local  是否是本地广播
     * */
    public static void sendBroadcastReceiver(Context mContext, Intent intent, boolean local) {
        if (mContext != null) {
            if (local) {
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            } else {
                mContext.sendBroadcast(intent);
            }
        }
    }



    /**
     * 判断是否连通网络
     */
    public static boolean isConnectNet(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo Mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo Wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //网络已连接
            return Mobile != null && Wifi != null && NetworkInfo.State.CONNECTED.equals(Mobile.getState()) || NetworkInfo.State.CONNECTED.equals(Wifi.getState());
        }
        return false;
    }

    /**
     * A-->B页面,A向左滑出屏幕,B从屏幕右侧滑动屏幕中间
     */
    public static void openNewActivityAnim(Activity act, boolean finish) {
        if (act != null) {
            act.overridePendingTransition(R.anim.push_right_to_middle_in, R.anim.push_middle_to_left_out);
            if (finish) {
                act.finish();
            }
        }
    }

    /**
     * B-->A页面,B从屏幕中间向右测出，A从左至右滑到屏幕中间,
     */
    public static void exitActivityAndBackAnim(Activity act, boolean finish) {
        if (act != null) {
            act.overridePendingTransition(R.anim.push_left_to_middle_in, R.anim.push_middle_to_right_out);
            if (finish) {
                act.finish();
            }
        }
    }

    /**
     * 进入WebView界面
     * @param context 上下文对象
     * @param url webView url
     * @param title webView标题
     * @param isHidden 是否隐藏分享按钮
     * */
    public static void intentWebView(Context context,String url,String title,boolean isHidden){
        Intent intent = new Intent(context, WebViewUI.class);
        intent.putExtra("loadUrl", url);
        intent.putExtra("title", title);
        intent.putExtra("isHiddenRightBtn", isHidden);
        context.startActivity(intent);
        Utils.openNewActivityAnim((Activity) context, false);
    }


    public static int getVersionCode(Context mContext) {
        PackageManager packageManager = mContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 110;
    }

    public static String getVersionName(Context mContext) {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1.2.0";
    }

    /**
     * 获取友盟渠道号
     *
     * @param mContext
     * @return
     */
    public static String getChannel(Context mContext) {
        String channel = "wsc";
        try {
            ApplicationInfo appInfo;
            appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 隐藏软键盘
     */
    public static void hiddenKeyBoard(Activity activity) {

        try {
            if (activity == null) return;
            // 取消弹出的对话框
            InputMethodManager manager = (InputMethodManager) activity.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager.isActive()) { // 只有在键盘正处于弹出状态时再去隐藏  by:KNothing
                if (activity.getCurrentFocus() == null) {
                    return;
                }
                manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 强制弹出
     */
    public static void showKeyBoard(EditText edit) {
        try {
            if (edit == null) return;
            edit.requestFocus();
            InputMethodManager m = (InputMethodManager)edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机IMEI号
     */
    public static String getIMEI(Context mContext) {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    /**
     * 系统时间是奇葩的10位数 传10位数的时间秒值</font>
     */
    public static String eventDetailTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return format.format(time * 1000);
    }

    /**
     * 系统时间是奇葩的10位数 传10位数的时间秒值</font>
     */
    public static String formatHourMinTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time * 1000);
    }

    public static String formatGroupTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(time * 1000);
    }

    public static String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(time * 1000);
    }

    /**
     * 记录应用错误日志时使用，by : KNothing
     */
    public static final String getSimpDate() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        currentDate = Calendar.getInstance().getTime();
        return formatter.format(currentDate);
    }



    /**
     * 邀约请求时间格式转换 <br><font color='red'>注意 系统时间是奇葩的10位数 需要乘以1000</font>
     */
    @SuppressLint("SimpleDateFormat")
    public static String eventInviteTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return format.format(time * 1000);
    }


    public static String convertBirthdayTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time * 1000);
    }

    public static String convertMoneyTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(time * 1000);
    }

    public static String convertMonthDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        return format.format(time * 1000);
    }

    //转年月
    public static String convertYearMonth(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(time * 1000);
    }

    //转月日
    public static String convertMonthToDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(time * 1000);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getLastUpdateTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日   HH:mm");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String tmStr = formatter.format(curDate);
        return tmStr;
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDialogDatingTime(long times) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date curDate = new Date(times);// 获取当前时间
        String tmStr = formatter.format(curDate);
        return tmStr;
    }

    public static String getScreenPixels(Context mContext) {
        if (mContext == null) return "720*1280";

        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int height = mContext.getResources().getDisplayMetrics().heightPixels;
        return String.valueOf(width).concat("*").concat(String.valueOf(height));
    }


    /**
     * 格式化未读条目数
     */
    public static void formatUnreadCount(TextView tv, int unread) {
        if (unread < 1) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            if (unread < 100) {//打开注释 显示全部未读条目数
                tv.setText(unread + "");
            } else {
                tv.setText("99+");
            }
        }

    }

    /**
     * 回收每个引导页面占用的内存
     *
     * @param iv
     */
    public static void recycleImageBg(View iv) {
        try {
            BitmapDrawable bd = (BitmapDrawable) iv.getBackground();
            if (bd.getBitmap() != null) {
                Bitmap bm = bd.getBitmap();
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                    bm = null;
                    iv.setBackgroundResource(0);
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 回收每个引导页面占用的内存
     *
     * @param iv
     */
    public static void recycleImageResource(ImageView iv) {
        try {
            BitmapDrawable bd = (BitmapDrawable) iv.getDrawable();
            if (bd.getBitmap() != null) {
                Bitmap bm = bd.getBitmap();
                if (bm != null && !bm.isRecycled()) {
                    bm.recycle();
                    bm = null;
                    iv.setImageResource(0);
                }
            }
        } catch (Exception e) {
        }
    }

    public static Bitmap getLocalBitmap(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }

    public static Bitmap getResourceBitmap(Context c, int resourceId) {
        Bitmap bitmap = null;
        try {
            InputStream is = c.getResources().openRawResource(resourceId);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (Exception e) {
            e.printStackTrace();

        } catch (Error e) {
            e.printStackTrace();

        }
        return bitmap;
    }

    public static void openResource(Context c, int resourceId, View v) {
        try {
            InputStream is = c.getResources().openRawResource(resourceId);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            v.setBackgroundDrawable(drawable);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public static void openResourceImageView(Context c, int resourceId, ImageView v) {
        try {
            InputStream is = c.getResources().openRawResource(resourceId);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            v.setImageBitmap(bitmap);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public static void openLocalImagaView(String path, ImageView v) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        v.setImageBitmap(bitmap);
    }


    /**
     * 计算字符串长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 替换回车换行
     */
    public static String replyEnter(String value) {
        if (value == null) {
            value = "";
        }
        return value.replace("\\n", "").replace("\\r", "");
    }

    /**
     * 从字节流获取图片
     * @param bytes
     * @param opts
     * @return
     */
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }

    /**
     * 读取流
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }



    /**
     * @param name json文件名
     * @return void
     * @TODO 保存json到本地
     */
    public static void writeToFile(JSONObject response, String name) {
        try {//保存到本地
            File jsonFile = new File(SDCardCtrl.getFilePath() + File.separator + name);
            File dirs = new File(jsonFile.getParent());
            dirs.mkdirs();
            jsonFile.createNewFile();
            FileOutputStream fileOutS = new FileOutputStream(jsonFile);
            byte[] buf = response.toString().getBytes();
            fileOutS.write(buf);
            fileOutS.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param name json文件名
     * @return void
     * @TODO 读取json从本地
     */
    public static String readFromFile(String name) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(SDCardCtrl.getFilePath() + File.separator + name);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
//            res = EncodingUtils.getString(buffer, "UTF-8");
            res = new String(buffer,"UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * by : cyt
     *
     * @return void
     * @TODO 删除本地缓存的文件
     */
    public static void deleteFiles(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFiles(f);
            }
            file.delete();
        }
    }

     /**
     * 防止连击
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
