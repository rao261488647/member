package com.frame.member.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.LoginCodeParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.TimeCount;
import com.frame.member.bean.BaseBean;
import com.frame.member.bean.LoginCodeResult;

public class PasswordFindActivity extends BaseActivity implements OnClickListener {

	TextView tv_code_send,tv_login_button;
	EditText et_phone_num,et_code,et_password,et_password_confirm;
	TimeCount timer;
	private LinearLayout ll_back_login;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_password_forget_1);
	}

	@Override
	protected void findViewById() {
		tv_code_send = (TextView) findViewById(R.id.tv_code_send);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
		et_phone_num = (EditText) findViewById(R.id.et_phone_num);
		et_code = (EditText) findViewById(R.id.et_code);
		et_password = (EditText) findViewById(R.id.et_password);
		et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
		ll_back_login = (LinearLayout) findViewById(R.id.ll_back_login);
	}

	@Override
	protected void setListener() {
		tv_code_send.setOnClickListener(this);
		tv_login_button.setOnClickListener(this);
		ll_back_login.setOnClickListener(this);
		
	}

	

	@Override
	protected void processLogic() {
		timer = new TimeCount(60000, 1000, tv_code_send, 0, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_code_send:
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else{
				toGetCode();
				
			}
			
			break;
		case R.id.tv_login_button: 		//找回密码
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else if(TextUtils.isEmpty(et_code.getText().toString())){
				showToast("请输入验证码");
			}else if(TextUtils.isEmpty(et_password.getText().toString())){
				showToast("请输入密码");
			}else if(TextUtils.isEmpty(et_password_confirm.getText().toString())){
				showToast("请确认密码");
			}else if(!et_password_confirm.getText().toString().equals(et_password.getText().toString())){
				showToast("两次输入的密码不一致！");
			}else{
				toGetPasswordBack();
			}	
			
			
			break;
		case R.id.ll_back_login:
			finish();
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			break;
		case R.id.tv_register_agreement:
			startActivity(new Intent(PasswordFindActivity.this,RegisterAgreementActivity.class));
			break;
		}
	}
	
	//请求验证码接口
	private void toGetCode(){
		BaseParser<LoginCodeResult> parser = new LoginCodeParser();
		HttpRequestImpl request = new HttpRequestImpl(PasswordFindActivity.this,
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
	//找回密码
		private void toGetPasswordBack(){
			BaseParser<BaseBean> parser = new NoBackParser();
			HttpRequestImpl request = new HttpRequestImpl(PasswordFindActivity.this,
					AppConstants.APP_SORT_STUDENT+"/findpwd", parser,HttpRequest.RequestMethod.post);
			
			request.addParam("mobile", et_phone_num.getText().toString())
					.addParam("verificationCode", et_code.getText().toString())
					.addParam("password", et_password.getText().toString());
			DataCallback<BaseBean> callback1 = new DataCallback<BaseBean>() {

				@Override
				public void processData(BaseBean object, RequestResult result) {
					if(result == RequestResult.Success){
						if(object != null){
							showToast(object.message);
							finish();
							overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
						}
					}
				}
			};
			getDataFromServer(request, callback1);
		}
		
	
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
