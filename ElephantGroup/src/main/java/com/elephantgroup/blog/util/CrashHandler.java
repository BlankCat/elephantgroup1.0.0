package com.elephantgroup.blog.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 异常日志收集类
 */
public class CrashHandler implements UncaughtExceptionHandler {
	
	/** CrashHandler实例 */
	private static CrashHandler instance;
	/** 程序的Context对象 */
	private Context mContext;
	/** 系统默认的UncaughtException处理类 */
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() { }

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if (instance == null)
			instance = new CrashHandler();
		return instance;
	}

	/**
	 * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
	 */
	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
		ex.printStackTrace();
//		final String msg = ex.getLocalizedMessage();
		// 收集设备信息
		// collectCrashDeviceInfo(mContext);
		// 保存错误报告文件
		String crashFileName = saveCrashInfoToFile(ex);
		// 发送错误报告到服务器
		// sendCrashReportsToServer(mContext);
		return true;
	}

	/**
	 * 保存错误信息到文件中
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		
		String chanel = "iyueni";
		try {
			try {
				ApplicationInfo mInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
				chanel = mInfo.metaData.getString("UMENG_CHANNEL");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			String result = getErrorInfo(ex);
			StringBuilder sb = new StringBuilder();
			result = sb
					.append("手机型号 = ").append(android.os.Build.MODEL).append("\n")
					.append("系统版本 = ").append(android.os.Build.VERSION.RELEASE).append("\n")
					.append("应用版本 = ").append(Utils.getVersionName(mContext)).append("\n") 
					.append("应用渠道 = ").append(chanel).append("\n")
					.append("崩溃时间 = ").append(Utils.eventDetailTime(System.currentTimeMillis() / 1000)).append("\n")
					.append(result).toString();
			return SDFileUtils.saveCrashInfoToFile(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/** 
　　* 获取错误的信息 
　　* @param arg1 
　　* @return 
　　*/ 
	private String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw); 
		pw.close(); 
		String error= writer.toString();
		return error; 
	} 
}