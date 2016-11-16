package com.elephantgroup.blog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MySharedPrefs {

    public static final String FILE_USER = "userinfo";
    public static final String FILE_APP = "application";
    public static final String KEY_LOGIN_USERINFO = "user_userinfo";//个人信息

    public static String readString(Context context, String fileName, String key) {
        if (context == null) return "";
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public static void clearUserInfo(Context context) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN_USERINFO, "");
        editor.commit();
    }

    public static void write(Context context, String fileName, String key, String value) {
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 写入boolean开关值
     * @param context
     * @param fileName
     * @param key
     * @param value
     */
    public static void writeBoolean(Context context, String fileName, String key, boolean value) {
        if (context == null)
            return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void writeInt(Context context, String fileName, final String key, final int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int readInt(Context context, String fileName, String key) {
        if (context == null) return 0;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }


    public static int readInt1(Context context, String fileName, String key) {
        if (context == null) return -1;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }


    /**
     * @param context
     * @param fileName
     * @param key
     */
    @Deprecated
    public static boolean readBoolean(Context context, String fileName, String key) {
        if (context == null) return true;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public static boolean readBooleanNormal(Context context, String fileName, String key) {
        if (context == null) return false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 移除登录时存储的用户信息JSON串
     */
    public static void removeUserPrefs(Context c) {
        if (c == null) return;
        SharedPreferences sharedPreferences = c.getSharedPreferences(MySharedPrefs.FILE_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(MySharedPrefs.KEY_LOGIN_USERINFO);
        editor.commit();
    }

    /**
     * 删除key值
     */
    public static void remove(Context c, String key) {
        if (c == null) return;
        SharedPreferences sharedPreferences = c.getSharedPreferences(MySharedPrefs.FILE_USER, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 删除key值
     */
    public static void removeCache(Context c, String fileName, String key) {
        if (c == null) return;
        SharedPreferences sharedPreferences = c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 遍历所有值
     * @return
     */
    public static Map<String, ?> getall(Context c) {
        if (c == null) return null;
        SharedPreferences sharedPreferences = c.getSharedPreferences(MySharedPrefs.FILE_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }


    public static JSONArray readJsonArray(Context context, String fileName, String key) {
        if (context == null) return null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int size = sharedPreferences.getInt(key + "size", 0);
        JSONArray array = new JSONArray();
        for (int i = 0; i < size; i++) {
            try {
                array.put(i, new JSONObject(sharedPreferences.getString(key + "value" + i, "")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

}
