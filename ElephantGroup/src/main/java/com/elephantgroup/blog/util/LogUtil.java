package com.elephantgroup.blog.util;

import android.util.Log;

/**
 * Created by zhang on 2016/3/7.
 */
public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int WTF=6;
    public static final int NOTHING = 7;
    public static final int LEVEL = VERBOSE;

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[3];
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void v(String content) {
        if (LEVEL <= VERBOSE) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.v(tag, content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (LEVEL <= VERBOSE) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.v(tag, content, tr);
        }
    }

    public static void d(String content) {
        if (LEVEL <= DEBUG) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.d(tag, content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (LEVEL <= DEBUG) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.d(tag, content, tr);
        }
    }

    public static void e(String content) {
        if (LEVEL <= ERROR) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.e(tag, content);
        }
    }

    public static void e(String content, Throwable tr) {
        if (LEVEL <= ERROR) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.e(tag, content, tr);
        }
    }

    public static void i(String content) {
        if (LEVEL <= INFO) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (LEVEL <= INFO) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.i(tag, content, tr);
        }
    }


    public static void w(String content) {
        if (LEVEL <= WARN) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.w(tag, content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (LEVEL <= WARN) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.w(tag, content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (LEVEL <= WARN) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.w(tag, tr);
        }
    }


    public static void wtf(String content) {
        if (LEVEL <= WTF) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.wtf(tag, content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (LEVEL <= WTF) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.wtf(tag, content, tr);
        }
    }

    public static void wtf(Throwable tr) {
        if (LEVEL <= WTF) {
            StackTraceElement caller = getCallerStackTraceElement();
            String tag = generateTag(caller);
            Log.wtf(tag, tr);
        }
    }

}
