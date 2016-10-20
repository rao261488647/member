package com.frame.member.AppConstants;

import android.os.Environment;

public class AppConstants {

//    public static final String URI_DEV = "http://www.99lc.com:8087/iBusiness/rs/cws/";   //正式服            
//    public static final String URI_DEV = "http://183.62.205.180:8043/iBusiness/rs/cws/";   //正式服 
//    public static final String URI_DEV = "http://api.flowerski.com/";   //正式服 
    public static final String URI_DEV = "http://testapi.flowerski.com/";   //测试服 
    
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
    
    public static final String GETMVSINFO = "/getmvsinfo";
    public static final String GETMVSCOVER = "/getmvscover";
    public static final String MYVIDEODYNAMIC = "/myvideodynamic";
    
    public static final String APP_ID = "app001h23c9h16";
    public static final String APP_KEY = "e7d5fdb8180d25362292f84528395af5";
    
    //微信的
    public static final String APP_ID_WX = "wxaff36b3a7ba66e5f";
	
	/*获取当前系统的android系统版本*/
	public static final String currentapiVersion = "Android " + android.os.Build.VERSION.RELEASE;

	public static boolean isDebugMode = true;

	public final static String EXT_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	public final static String CACHE_DIR = EXT_PATH + "/TT_Cache";
	public final static String CLIP_VIDEO_CACHE = CACHE_DIR
			+ "/clip_video_cache/";
	public final static String IMAGE_CHCHE = CACHE_DIR
			+ "/image_cache";
	public final static String IMAGE_CHCHE_COMPRESSED = CACHE_DIR
			+ "/image_chche_compressed";

	
	public static final String ACTION_LOGIN_SUCC = "action_login_succ";

	public static final String FRAG_TITLE_KEY = "frag_title_key";
	
	
	public static final String BC_REG_SUCC = "bc_reg_succ";
	public static final String RENT_CAR_SUBMIT_SUCC = "rent_car_submit_succ";
	
	
	public static final String BC_PUSH_COMING = "bc_push_coming";
	
	
	public static final String ACTION_ACT_PUB_SUCC = "action_act_pub_succ";
	
	
	public static final String MAKE_COACH = "http://api.flowerski.com/v1/student/becoach"; //我要做教练网页地址
}
