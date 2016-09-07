package com.frame.member.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;

public class RegActivity extends BaseActivity implements OnClickListener {

	private EditText et_reg_phone, et_reg_verify, et_reg_pwd;
	private TextView tv_reg_verify, tv_reg;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_reg);
	}

	@Override
	protected void findViewById() {
		et_reg_phone = (EditText) findViewById(R.id.et_reg_phone);
		et_reg_verify = (EditText) findViewById(R.id.et_reg_verify);
		et_reg_pwd = (EditText) findViewById(R.id.et_reg_pwd);

		tv_reg = (TextView) findViewById(R.id.tv_reg);
		tv_reg_verify = (TextView) findViewById(R.id.tv_reg_verify);
	}

	@Override
	protected void setListener() {
		tv_reg.setOnClickListener(this);
		tv_reg_verify.setOnClickListener(this);
		//ll_title_left.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		tv_title.setText("注册");
		//ll_title_left.setVisibility(0);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.none, R.anim.slide_out_to_bottom);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_title_left:
			finish();
			overridePendingTransition(R.anim.none, R.anim.slide_out_to_bottom);
			break;
		case R.id.tv_reg:
			final String phone_reg = et_reg_phone.getText().toString().trim();
			String verify = et_reg_verify.getText().toString().trim();
			String pwd = et_reg_pwd.getText().toString().trim();

			if (TextUtils.isEmpty(phone_reg)) {
				showToast("手机号码不能为空");
				return;
			}

			if (!CommonUtil.isMobileNO(phone_reg)) {
				showToast("手机号码非法");
				return;
			}

			if (TextUtils.isEmpty(verify)) {
				showToast("验证码不能为空");
				return;
			}

			if (TextUtils.isEmpty(pwd)) {
				showToast("密码不能为空");
				return;
			}

			if (pwd.length() < 6) {
				showToast("密码不能小于6位");
				return;
			}

			HttpRequest request_reg = new HttpRequestImpl(RegActivity.this,
					AppConstants.REGISTER, null);

			request_reg.addParam("phoneNum", phone_reg)
					.addParam("password", pwd).addParam("verifyCode", verify)
					.addParam("device", CommonUtil.getDeviceModel()).addParam("imei", CommonUtil.getDeviceIMEI(RegActivity.this))
					.addParam("appSysVersion", CommonUtil.getAppVersion(RegActivity.this));

			getDataFromServer(request_reg, new CommCallBack(RegActivity.this,
					"注册成功", new CommCallBack.CallBackSucc() {

						@Override
						public void onRequestSucc(JSONObject object) {
							//保存注册信息
							String uid = object.optString("uid");
							if(!TextUtils.isEmpty(uid)){
								SPUtils.getAppSpUtil().put(RegActivity.this, SPUtils.KEY_MEMBERUSERID, uid);
								SPUtils.getAppSpUtil().put(RegActivity.this, SPUtils.KEY_PHONENUM, phone_reg);
								Intent intent = new Intent(RegActivity.this,MainActivity.class);
								startActivity(intent);
								
								sendBroadcast(new Intent(AppConstants.BC_REG_SUCC));
							}
						}
					}), "正在注册...");

			break;
		case R.id.tv_reg_verify: // 获取验证码
			String phone = et_reg_phone.getText().toString().trim();
			if (TextUtils.isEmpty(phone)) {
				showToast("手机号码不能为空");
				return;
			}

			if (!CommonUtil.isMobileNO(phone)) {
				showToast("手机号码非法");
				return;
			}

			HttpRequest request = new HttpRequestImpl(RegActivity.this,
					AppConstants.GETVERIFYMESSAGE, null);

			request.addParam("phoneNum", phone);

			getDataFromServer(request, new CommCallBack(RegActivity.this,
					"获取验证码成功", new CommCallBack.CallBackSucc() {

						@Override
						public void onRequestSucc(JSONObject object) {
							// et_reg_verify.setText(object.optString("verifyCode"));
							tv_reg_verify.setEnabled(false);
							handler.sendEmptyMessageDelayed(0, 30000);// 30秒后重新可以获取验证码
						}
					}), "正在获取验证码...");

			break;
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (!tv_reg_verify.isEnabled()) {
				tv_reg_verify.setEnabled(true);
			}
		};
	};

}
