package com.frame.member.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.RegisterLoginParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.RegisterLoginResult;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class LoginActivity extends BaseActivity implements OnClickListener {

	TextView tv_login_button;
	EditText et_phone_num,et_password;
	ImageView iv_login_weixin,iv_login_weibo,iv_login_qq;
	LinearLayout ll_back_login;
	private IWXAPI api;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void findViewById() {
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
//		tv_login2_button = (TextView) findViewById(R.id.tv_login2_button);
//		tv_title_left_login = (TextView) findViewById(R.id.tv_title_left_login);
//		tv_title_right_login = (TextView) findViewById(R.id.tv_title_right_login);
		et_phone_num = (EditText) findViewById(R.id.et_phone_num);
		et_password = (EditText) findViewById(R.id.et_password);
		iv_login_weixin = (ImageView) findViewById(R.id.iv_login_weixin);
		iv_login_weibo = (ImageView) findViewById(R.id.iv_login_weibo);
		iv_login_qq = (ImageView) findViewById(R.id.iv_login_qq);
//		rl_title_left = (RelativeLayout) findViewById(R.id.rl_title_left);
//		rl_title_right = (RelativeLayout) findViewById(R.id.rl_title_right);
//		view_title_left = findViewById(R.id.view_title_left);
//		view_title_right = findViewById(R.id.view_title_right);
		ll_back_login = (LinearLayout) findViewById(R.id.ll_back_login);
	}

	@Override
	protected void setListener() {
		iv_login_weixin.setOnClickListener(this);
		iv_login_weibo.setOnClickListener(this);
		iv_login_qq.setOnClickListener(this);
		tv_login_button.setOnClickListener(this);
//		tv_login2_button.setOnClickListener(this);
		ll_back_login.setOnClickListener(this);
		
	}

	

	@Override
	protected void processLogic() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_login_weixin:
			showToast("微信登录,请稍后..");
			//api注册 
			api = WXAPIFactory.createWXAPI(this, AppConstants.APP_ID_WX, true);
			api.registerApp(AppConstants.APP_ID_WX);

			SendAuth.Req req = new SendAuth.Req();
			  
			//授权读取用户信息  
			req.scope = "snsapi_userinfo";

			//自定义信息 
			req.state = "wechat_sdk_demo_test";

			//向微信发送请求
			api.sendReq(req);
			break;
		case R.id.iv_login_weibo:
			showToast("微博登录");
			
			break;	
		case R.id.iv_login_qq:
			showToast("QQ登录");
			break;
		
		case R.id.tv_login_button: 		//登录
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else if(TextUtils.isEmpty(et_password.getText().toString())){
				showToast("请输入密码");
			}else{
				toLogin();
				
			}
			
			
			break;
		case R.id.ll_back_login: 		//登录
			finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			
			break;
//		case R.id.tv_login2_button:		//登录
//			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
//				showToast("请输入手机号");
//			}else if(TextUtils.isEmpty(et_code.getText().toString())){
//				showToast("请输入验证码");
//			}else{
//				toLogin();
//			}
//			
//			break;
//		case R.id.rl_title_left:
//			if(isRight){
//				tv_title_left_login.setTextColor(0xffffffff);
//				tv_title_right_login.setTextColor(0xff878788);
//				tv_title_left_login.setTextSize(16);
//				tv_title_right_login.setTextSize(14);
//				view_title_left.setVisibility(View.VISIBLE);
//				view_title_right.setVisibility(View.INVISIBLE);
//				tv_login_button.setText("登录");
//				isRight = false;
//			}
//			break;
//		case R.id.rl_title_right:
//			if(!isRight){
//				tv_title_right_login.setTextColor(0xffffffff);
//				tv_title_left_login.setTextColor(0xff878788);
//				tv_title_right_login.setTextSize(16);
//				tv_title_left_login.setTextSize(14);
//				view_title_right.setVisibility(View.VISIBLE);
//				view_title_left.setVisibility(View.INVISIBLE);
//				tv_login_button.setText("注册");
//				isRight = true;
//			}
//			break;
		}
	}
	
//	//请求验证码接口
//	private void toGetCode(){
//		BaseParser<LoginCodeResult> parser = new LoginCodeParser();
//		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this,
//				AppConstants.APP_SORT_STUDENT+"/getappverify", parser,HttpRequest.RequestMethod.post);
//		
//		request.addParam("mobile", et_phone_num.getText().toString());
//		getDataFromServer(request, callback);
//	}
//	private DataCallback<LoginCodeResult> callback = new DataCallback<LoginCodeResult>() {
//
//		@Override
//		public void processData(LoginCodeResult object, RequestResult result) {
//			if(result == RequestResult.Success){
//				if(object != null){
//					if("200".equals(object.code)){
//						timer.start();
//						showToast(object.message);
//					}
//				}
//			}
//		}
//	};
	
//	// 注册接口
//	private void toLoginRegister(){
//		BaseParser<RegisterResult> parser = new RegisterParser();
//		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this, 
//				AppConstants.APP_SORT_STUDENT+"/appregister", parser);
//		request.addParam("mobile", et_phone_num.getText().toString())
//				.addParam("verificationCode", et_code.getText().toString());
////		request.addParam("mobile", et_code.getText().toString())
////				.addParam("verificationCode", et_phone_num.getText().toString());
//		getDataFromServer(request, callback2);
////		Intent it = new Intent(this,MainActivity.class);
////		startActivity(it);
//	}
//	
//	private DataCallback<RegisterResult> callback2 = new DataCallback<RegisterResult>() {
//		
//		@Override
//		public void processData(RegisterResult object, RequestResult result) {
//			if(result == RequestResult.Success){
//				if(null != object){
//					if("200".equals(object.code)){
//						showToast(object.code);
//						SPUtils.getAppSpUtil().put(
//								LoginActivity.this, SPUtils.KEY_PHONENUM, object.mobile);
//						SPUtils.getAppSpUtil().put(
//								LoginActivity.this, SPUtils.KEY_TOKEN, object.token);
//						startActivity(new Intent(LoginActivity.this,MainActivity.class));
//					}
//				}
//			}
//		}
//	};
	
	
	
	//登录接口
	private void toLogin(){
		BaseParser<RegisterLoginResult> parser = new RegisterLoginParser();
		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/applogin", parser);
		request.addParam("mobile", et_phone_num.getText().toString())
				.addParam("password", et_password.getText().toString());
//		request.addParam("mobile", et_code.getText().toString())
//				.addParam("verificationCode", et_phone_num.getText().toString());
		getDataFromServer(request, callback1);
	}
	private DataCallback<RegisterLoginResult> callback1 = new DataCallback<RegisterLoginResult>() {

		@Override
		public void processData(RegisterLoginResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						showToast(object.message);
						
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERUSERID, object.memberUserId);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERIDEN, object.memberIden);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERLLEVEL, object.memberlLevel);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERGRADE, object.memberGrade);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_ISTEACHER, object.isTeacher);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXOPENID, object.wxOpenId);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXHEAD, object.wxHead);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXNICHENG, object.wxNiCheng);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_PHONENUM, object.memberTel);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERNAME, object.memberName);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERSEX, object.memberSex);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERSIGN, object.memberSign);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERBIRTH, object.memberBirth);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERPROVINCE, object.memberProvince);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERCITY, object.memberCity);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERAREA, object.memberArea);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERADDRESS, object.memberAddress);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_REGTIME, object.regtime);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERPOINTS, object.memberPoints);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_ISOPEN, object.isOpen);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_UPDETIME, object.updeTime);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_RECOFOLLOW, object.recofollow);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_NOTICESET, object.noticeset);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERFROM, object.memberFrom);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_TOKEN, object.token);
							startActivity(new Intent(LoginActivity.this,MainActivity.class));
							
						}
				}
			}
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	


}
