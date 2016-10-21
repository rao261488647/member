package com.frame.member.Utils;

import com.frame.member.AppConstants.AppConstants;

import android.text.TextUtils;
import android.util.Log;

/***
 * 全局log打印
 * 
 * @author Arvin
 * 
 */
public class MyLog {

	/**
	 * 打印info级别的Log
	 * 
	 * @param msg
	 * @param tag
	 *            打印的Tag，如果省略，tag为context类名
	 */
	public static void i(String tag, String msg) {
		print(0, tag, msg);
	}

	/**
	 * 打印warn级别的Log
	 * 
	 * @param msg
	 * @param tag
	 */
	public static void w(String tag, String msg) {
		print(1, tag, msg);
	}

	/**
	 * 打印debug级别的Log
	 * 
	 * @param msg
	 * @param tag
	 */
	public static void d(String tag, String msg) {
		print(2, tag, msg);
	}

	/**
	 * 打印verb级别的Log
	 * 
	 * @param msg
	 * @param tag
	 */
	public static void v(String tag, String msg) {
		print(3, tag, msg);
	}

	/**
	 * 打印error级别的Log
	 * 
	 * @param msg
	 * @param tag
	 * 
	 */
	public static void e(String tag, String msg) {
		print(4, tag, msg);
	}

	/**
	 * 封装系统打印
	 * 
	 * @param type
	 * @param tag
	 * @param msg
	 */
	private static void print(int type, String tag, String msg) {
		if (TextUtils.isEmpty(msg) || !AppConstants.isDebugMode) // 打印内容为空
			return;

		String _tag = TextUtils.isEmpty(tag) ? "TT_Cookies" : tag;

		switch (type) {
		case 0:
			Log.i(_tag, msg);
			break;
		case 1:
			Log.w(_tag, msg);
			break;
		case 2:
			Log.d(_tag, msg);
			break;
		case 3:
			Log.v(_tag, msg);
			break;
		case 4:
			Log.e(_tag, msg);
			break;
		}
	}

	/**
	 * 堆栈打印模式 类似debug
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void printLogDetail(String tag) {
		// if (TextUtils.isEmpty(msg) || !AppConstants.isDebugMode)
		// return;

		StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		// System.out.println("---msg--->" + msg + "---File--->"
		// + ste.getFileName() + "----Mothod---->" + ste.getMethodName()
		// + "----LineNumber--->" + ste.getLineNumber());
		String msg = "File--->" + ste.getFileName() + "----Mothod---->"
				+ ste.getMethodName() + "----LineNumber--->"
				+ ste.getLineNumber();
		print(2, tag, msg);
	}

}
