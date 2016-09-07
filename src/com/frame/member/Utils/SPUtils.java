package com.frame.member.Utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
	// {
	// "uid": "402881e54e2a2418014e2a2503b90003",
	// "phoneNum": "8",
	// "desc": "",
	// "status": 200,
	// "nickName": "1111",
	// "location": "",
	// "profileImaeUrl":
	// "http://192.168.1.107:8080/upload/photo/1438061798.jpg",
	// "brithday": "",
	// "account": "8",
	// "gender": "男"
	// }
//	public static final String KEY_USERID = "key_userid";
	public static final String KEY_PHONENUM = "key_phonenum";
	
	//登录成功
	public static final String KEY_MEMBERUSERID = "key_memberuserid";//会员ID
	public static final String KEY_MEMBERIDEN = "key_memberiden";		//会员身份,1:普通会员 2:内部工作人员
	public static final String KEY_MEMBERLLEVEL = "key_memberllevel";	//0:非会员 1:绿卡 2:蓝卡 3:黑卡
	public static final String KEY_MEMBERGRADE = "key_membergrade";		//会员等级,0,1,2,3,4,5级
	public static final String KEY_ISTEACHER = "key_isteacher";			//0:非教练 1:教练
	public static final String KEY_WXOPENID = "key_wxOpenId";			//微信openid
	public static final String KEY_WXHEAD = "key_wxHead";				//微信头像
	public static final String KEY_WXNICHENG = "key_wxNiCheng";			//微信昵称
//	public static final String KEY_MEMBERTEL = "key_memberTel";			//会员手机号
	public static final String KEY_MEMBERNAME = "key_memberName";		//会员姓名
	public static final String KEY_MEMBERSEX = "key_memberSex";			//会员性别
	public static final String KEY_MEMBERSIGN = "key_memberSign";		//会员签名
	public static final String KEY_MEMBERBIRTH = "key_memberBirth";		//出生日期
	public static final String KEY_MEMBERPROVINCE = "key_memberProvince";//省
	public static final String KEY_MEMBERCITY = "key_memberCity";		//市
	public static final String KEY_MEMBERAREA = "key_memberArea";		//区
	public static final String KEY_MEMBERADDRESS = "key_memberAddress";	//详细地址
	public static final String KEY_REGTIME = "key_regtime";				//注册时间
	public static final String KEY_MEMBERPOINTS = "key_memberPoints";	//积分
	public static final String KEY_MEMBERMONEY = "key_memberMoney";		//帐户余额
	public static final String KEY_ISOPEN = "key_isOpen";				//是否开放 0:屏蔽 1:开放
	public static final String KEY_UPDETIME = "key_updeTime";			//升级时间
	public static final String KEY_RECOFOLLOW = "key_recofollow";		//推荐关注,0:否 1:是
	public static final String KEY_NOTICESET = "key_noticeset";			//APP通知提醒设置,0:未开启 1:开启举例：赞|雪友评论|教练点评,0|0|1
	public static final String KEY_MEMBERFROM = "key_memberFrom";		//会员来源,1:微信 2:学滑雪APP 3:教练APP
	public static final String KEY_TOKENTIME = "key_tokenTime";			//当前时间戳

	public static final String KEY_PROFILEIMAGEURL = "key_profileImageUrl";
	public static final String KEY_FOLLOWERSCOUNT = "key_followersCount";
	public static final String KEY_FRIENDSCOUNT = "key_friendsCount";
	public static final String KEY_NICKNAME = "key_nickName";
	public static final String KEY_TOKEN = "key_token";
	public static final String KEY_TOKEN_LOGIN = "key_token_login";
	public static final String KEY_DESC = "key_desc";
	public static final String KEY_LOCATION = "key_location";
	public static final String KEY_BRITHDAY = "key_brithday";
	public static final String KEY_ACCOUNT = "key_account";
	public static final String KEY_PASSWORD = "key_password";
	public static final String KEY_GENDER = "key_gender";
	public static final String KEY_LEVEL = "key_level";
	public static final String KEY_TOTALINTEGRAL = "key_totalIntegral";
	public static final String KEY_EXPERIENCE = "key_experience";
	public static final String KEY_GOLD = "key_gold";
	public static final String KEY_TYPE = "key_type";
	
	public static final String KEY_USERS_PROFILE = "key_users_profile";

	private SPUtils() {
	}

	private static SPUtils mSpUtils = new SPUtils();

	public static SPUtils getAppSpUtil() {
		return mSpUtils;
	}

	public final static String FILE_NAME = "cookie_config";

	public void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	public boolean putWithResult(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		return editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);

	}

	/**
	 * 异步兼容类 提高效率
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}