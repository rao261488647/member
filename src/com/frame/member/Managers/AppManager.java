package com.frame.member.Managers;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

import com.frame.member.Utils.MyLog;

/**
 * 
 * @类名： AppManager
 * @描述： Activity栈 存放所有的Acitivity 管理其退出和进入
 * @作者： Arvin
 * @创建时间： 2013 2013年12月20日 上午10:09:48
 * @修改时间：
 * @修改描述：
 * 
 */
public class AppManager {
	public static final String TAG = "AppManager";
	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * @方法： getAppManager
	 * @描述： TODO 单一实例
	 * @return
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * @方法： addActivity
	 * @描述： TODO 添加Activity到堆栈
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity栈的数量
	 * 
	 * @return
	 */
	public int getActivityAcount() {
		return activityStack.size();
	}

	/**
	 * @方法： currentActivity
	 * @描述： TODO 获取当前Activity（堆栈中最后一个压入的）
	 * @return
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * @方法： finishActivity
	 * @描述： TODO 结束当前Activity（堆栈中最后一个压入的）
	 * @param activity
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * @方法： finishActivity
	 * @描述： TODO 结束指定的Activity
	 * @param cls
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * @方法： AppExit
	 * @描述： TODO 结束指定类名的Activity
	 * @param context
	 */
	public void finishActivity(Class<?> cls) {
		Activity iActivity = null;
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				iActivity = activity;
			}
		}
		finishActivity(iActivity);
	}

	/**
	 * @方法： finishAllActivity
	 * @描述： TODO 结束所有Activity
	 * @param context
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * @方法： AppExit
	 * @描述： TODO 退出应用程序
	 * @param context
	 */
	public void AppExit(Context context) {
		try {
			/** 记录流量 **/
			finishAllActivity();
			// ActivityManager activityMgr = (ActivityManager) context
			// .getSystemService(Context.ACTIVITY_SERVICE);
			// activityMgr.restartPackage(context.getPackageName());
			// 注释原因:为了保持后台服务的退出后运行
			// activityMgr.killBackgroundProcesses(context.getPackageName());
			// System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * @方法： StringLog
	 * @描述： TODO 打印当前所有的activity
	 * @param context
	 */
	public void StringLog(Context context) {
		for (int i = 0; i < activityStack.size(); i++) {
			MyLog.i(TAG, "====" + activityStack.get(i).getClass().getName());
		}
	}
}