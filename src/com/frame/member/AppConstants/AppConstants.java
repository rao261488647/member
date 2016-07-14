package com.frame.member.AppConstants;

import android.os.Environment;

public class AppConstants {

//    public static final String URI_DEV = "http://www.99lc.com:8087/iBusiness/rs/cws/";   //正式服            
//    public static final String URI_DEV = "http://183.62.205.180:8043/iBusiness/rs/cws/";   //正式服 
    public static final String URI_DEV = "http://api.flowerski.com/";   //正式服 
    
    public static final String APP_VERSION = "v1";  //版本
    public static final String APP_SORT_STUDENT = "/student";  //学生接口类别
    public static final String APP_SORT_COACH = "/coach";      //教练接口类别
    
    public static final String SAVE_INSTANCE_KEY = "key_save_instance";
    
    public static final String GETVERIFYMESSAGE = "getVerifyMessage";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String MODIFYPASSWORD = "modifyPassword";
    public static final String GETANNOUNCEMENTLIST = "getAnnouncementList";
    public static final String RENTCARLIST = "rentCarList";
    public static final String SPECCARPERREGISTER = "specCarPerRegister";
    public static final String STATEMENTSUBMIT = "statementSubmit";
    public static final String PAYFEESUBMIT = "payFeeSubmit";
    public static final String NOTICETOREAD = "noticeToRead";
    public static final String NOTICELIST = "noticeList";
    public static final String UPLOADTOKEN = "uploadToken";
    public static final String PROFILEINFO = "profileInfo";
    public static final String STATEMENTLIST = "statememtList";
    public static final String CONTRACTINFO = "contractInfo";
    public static final String DISSCONTRACT = "dissContract";
    public static final String PAYFEELIST = "payFeeList";
	
	/*获取当前系统的android系统版本*/
	public static final String currentapiVersion = "Android " + android.os.Build.VERSION.RELEASE;

	public static boolean isDebugMode = true;

	public final static String EXT_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	public final static String CACHE_DIR = EXT_PATH + "/TT_Cache";
	public final static String IMAGE_CHCHE = CACHE_DIR
			+ "/image_cache";
	public final static String IMAGE_CHCHE_COMPRESSED = CACHE_DIR
			+ "/image_chche_compressed";
	//QQid
	public static final String QQ_ID="1104844553";
	//微信id
	public static final String WX_ID = "wxa687db4285341669";
	public static final String WX_SECRET = "84d80894731ebfa5701e5da8a2871117";
	//微博key
	public static final String APP_KEY = "1660431280";
	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	
	public static final String ACTION_LOGIN_SUCC = "action_login_succ";

	public static final String FRAG_TITLE_KEY = "frag_title_key";
	
	
	public static final String BC_REG_SUCC = "bc_reg_succ";
	public static final String RENT_CAR_SUBMIT_SUCC = "rent_car_submit_succ";
	
	
	public static final String BC_PUSH_COMING = "bc_push_coming";

}
