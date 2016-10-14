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
import com.frame.member.Parsers.LoginCodeParser;
import com.frame.member.Parsers.RegisterParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.Utils.TimeCount;
import com.frame.member.bean.LoginCodeResult;
import com.frame.member.bean.RegisterResult;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	TextView tv_code_send,tv_login_button,tv_register_agreement;
	EditText et_phone_num,et_code;
	ImageView iv_login_weixin,iv_login_weibo,iv_login_qq;
	TimeCount timer;
	private LinearLayout ll_back_login;
	private IWXAPI api;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_register);
	}

	@Override
	protected void findViewById() {
		tv_code_send = (TextView) findViewById(R.id.tv_code_send);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
		tv_register_agreement = (TextView) findViewById(R.id.tv_register_agreement);
//		tv_login2_button = (TextView) findViewById(R.id.tv_login2_button);
//		tv_title_left_login = (TextView) findViewById(R.id.tv_title_left_login);
//		tv_title_right_login = (TextView) findViewById(R.id.tv_title_right_login);
		et_phone_num = (EditText) findViewById(R.id.et_phone_num);
		et_code = (EditText) findViewById(R.id.et_code);
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
		tv_code_send.setOnClickListener(this);
		tv_login_button.setOnClickListener(this);
		ll_back_login.setOnClickListener(this);
		tv_register_agreement.setOnClickListener(this);
		
	}

	

	@Override
	protected void processLogic() {
		timer = new TimeCount(60000, 1000, tv_code_send, 0, this);
//		startActivity(new Intent(RegisterActivity.this,PasswordConfigActivity.class));
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
		case R.id.tv_code_send:
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else{
				toGetCode();
				
			}
			
			break;
		case R.id.tv_login_button: 		//注册or登录
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else if(TextUtils.isEmpty(et_code.getText().toString())){
				showToast("请输入验证码");
			}else{
				toLoginRegister();
				
			}
			
			
			break;
		case R.id.ll_back_login:
			finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.tv_register_agreement:
			startActivity(new Intent(RegisterActivity.this,RegisterAgreementActivity.class));
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
	
	//请求验证码接口
	private void toGetCode(){
		BaseParser<LoginCodeResult> parser = new LoginCodeParser();
		HttpRequestImpl request = new HttpRequestImpl(RegisterActivity.this,
				AppConstants.APP_SORT_STUDENT+"/getappverify", parser,HttpRequest.RequestMethod.post);
		
		request.addParam("mobile", et_phone_num.getText().toString());
		getDataFromServer(request, callback);
	}
	private DataCallback<LoginCodeResult> callback = new DataCallback<LoginCodeResult>() {

		@Override
		public void processData(LoginCodeResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						timer.start();
						showToast(object.message);
					}
				}
			}
		}
	};
	
	// 注册接口
	private void toLoginRegister(){
		BaseParser<RegisterResult> parser = new RegisterParser();
		HttpRequestImpl request = new HttpRequestImpl(RegisterActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/appregister", parser);
		request.addParam("mobile", et_phone_num.getText().toString())
				.addParam("verificationCode", et_code.getText().toString());
//		request.addParam("mobile", et_code.getText().toString())
//				.addParam("verificationCode", et_phone_num.getText().toString());
		getDataFromServer(request, callback2);
	}
	
	private DataCallback<RegisterResult> callback2 = new DataCallback<RegisterResult>() {
		
		@Override
		public void processData(RegisterResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						showToast(object.code);
						SPUtils.getAppSpUtil().put(
								RegisterActivity.this, SPUtils.KEY_PHONENUM, object.mobile);
						SPUtils.getAppSpUtil().put(
								RegisterActivity.this, SPUtils.KEY_TOKEN_LOGIN, object.token);
						SPUtils.getAppSpUtil().put(
								RegisterActivity.this, SPUtils.KEY_MEMBERUSERID, ""+object.memberUserId);
						startActivity(new Intent(RegisterActivity.this,PasswordConfigActivity.class));
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
