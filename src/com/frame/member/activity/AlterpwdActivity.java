package com.frame.member.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.TextUtils;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Utils.CommCallBack;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;

public class AlterpwdActivity extends BaseActivity implements OnDateSetListener {

	private RelativeLayout rl_old_pwd, rl_new_pwd, rl_renew_pwd;
	private TextView tv_old_pwd, tv_new_pwd, tv_renew_pwd, tv_alter_pwd_submit;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_alterpwd_mgr);
	}

	@Override
	protected void findViewById() {
		rl_old_pwd = (RelativeLayout) findViewById(R.id.rl_old_pwd);
		rl_new_pwd = (RelativeLayout) findViewById(R.id.rl_new_pwd);
		rl_renew_pwd = (RelativeLayout) findViewById(R.id.rl_renew_pwd);

		tv_old_pwd = (TextView) findViewById(R.id.tv_old_pwd);
		tv_new_pwd = (TextView) findViewById(R.id.tv_new_pwd);
		tv_renew_pwd = (TextView) findViewById(R.id.tv_renew_pwd);

		tv_alter_pwd_submit = (TextView) findViewById(R.id.tv_alter_pwd_submit);

		tv_title.setText("修改密码");
	}

	@Override
	protected void setListener() {

		rl_old_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(AlterpwdActivity.this);
				//phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);

				new AlertDialog.Builder(AlterpwdActivity.this)
						.setView(phone)
						.setTitle("请输入原始密码")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (!"".equals(phone.getText()
												.toString())) {
											tv_old_pwd.setText(phone.getText()
													.toString());
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rl_new_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(AlterpwdActivity.this);
				//phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);

				new AlertDialog.Builder(AlterpwdActivity.this)
						.setView(phone)
						.setTitle("请输入新密码")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (!"".equals(phone.getText()
												.toString())) {
											tv_new_pwd.setText(phone.getText()
													.toString());
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rl_renew_pwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(AlterpwdActivity.this);
				//phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);

				new AlertDialog.Builder(AlterpwdActivity.this)
						.setView(phone)
						.setTitle("请再次输入新密码")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (!"".equals(phone.getText()
												.toString())) {
											tv_renew_pwd.setText(phone.getText()
													.toString());
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		// 密码修改提交
		tv_alter_pwd_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String tv_old_pwd_val = tv_old_pwd.getText().toString().trim();
				String tv_new_pwd_val = tv_new_pwd.getText().toString().trim();
				String tv_renew_pwd_str = tv_renew_pwd.getText().toString()
						.trim();

				if (TextUtils.isEmpty(tv_old_pwd_val)) {
					showToast("请输入原始密码");
					return;
				}
				if (TextUtils.isEmpty(tv_new_pwd_val)) {
					showToast("请再次输入新密码");
					return;
				}
				if (TextUtils.isEmpty(tv_renew_pwd_str)) {
					showToast("请再次输入新密码");
					return;
				}

				HttpRequest request_reg = new HttpRequestImpl(
						AlterpwdActivity.this, AppConstants.MODIFYPASSWORD,
						null);

				request_reg
						.addParam(
								"phoneNum",
								(String) SPUtils.getAppSpUtil().get(
										AlterpwdActivity.this,
										SPUtils.KEY_PHONENUM, ""))
						.addParam("oldPassword", tv_old_pwd_val)
						.addParam("newPassword", tv_new_pwd_val);
				
				getDataFromServer(request_reg, new CommCallBack(
						AlterpwdActivity.this, "密码修改成功",
						new CommCallBack.CallBackSucc() {
							@Override
							public void onRequestSucc(JSONObject object) {
								finish();
							}
						}), "正在提交...");

			}
		});
	}

	@Override
	protected void processLogic() {
		iv_title_back.setVisibility(0);
	}

	private boolean isVibrate() {
		return true;
	}

	private boolean isCloseOnSingleTapDay() {
		return false;
	}



	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}
}
