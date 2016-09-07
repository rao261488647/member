package com.frame.member.activity;
/**
 *  密码设置
 *  @author long
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.BaseBean;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PasswordConfigActivity extends BaseActivity implements OnClickListener{
	
	private EditText et_password,et_password_confirm;
	private LinearLayout ll_back_login;
	private TextView tv_complete;
	
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_password_config);
	}

	@Override
	protected void findViewById() {
		et_password = (EditText) findViewById(R.id.et_password);
		et_password_confirm = (EditText) findViewById(R.id.et_password_confirm);
		ll_back_login = (LinearLayout) findViewById(R.id.ll_back_login);
		tv_complete = (TextView) findViewById(R.id.tv_complete);
	}
	
	@Override
	protected void setListener() {
		ll_back_login.setOnClickListener(this);
		tv_complete.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back_login:
			finish();
			break;
		case R.id.tv_complete:
			if(TextUtils.isEmpty(et_password.getText().toString())){
				showToast("密码不能为空");
			}else if(et_password.getText().toString().equals(et_password_confirm.getText().toString())){
				setPwd();
			}else{
				showToast("两次密码不一致");
			}
			break;

		}
	}
	private void setPwd(){
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(
				this, AppConstants.APP_SORT_STUDENT+"/setpwd", parser);
		request.addParam("mobile", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_PHONENUM, ""))
			.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN_LOGIN, ""))
			.addParam("password", et_password.getText().toString());
		getDataFromServer(request, callBack);
	}
	private DataCallback<BaseBean> callBack = new DataCallback<BaseBean>() {

		@Override
		public void processData(BaseBean object, RequestResult result) {
			if(object != null){
				SPUtils.getAppSpUtil().put(
						PasswordConfigActivity.this, SPUtils.KEY_PASSWORD, 
						et_password.getText().toString());
				startActivity(new Intent(PasswordConfigActivity.this,PersonInfoActivity.class));
				
			}
		}
	};
}
