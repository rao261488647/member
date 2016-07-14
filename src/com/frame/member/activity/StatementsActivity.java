package com.frame.member.activity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.util.TextUtils;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
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

public class StatementsActivity extends BaseActivity implements
		OnDateSetListener {

	private RelativeLayout rl_didi_statments, rl_youbu_statments,
			rl_statments_date;
	private TextView tv_didi_statments, tv_youbu_statments, tv_statments_date,
			tv_statments_submit;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_statments_mgr);
	}

	@Override
	protected void findViewById() {
		rl_didi_statments = (RelativeLayout) findViewById(R.id.rl_palt_statments);
		rl_youbu_statments = (RelativeLayout) findViewById(R.id.rl_money_statments);
		rl_statments_date = (RelativeLayout) findViewById(R.id.rl_statments_date);

		tv_didi_statments = (TextView) findViewById(R.id.rl_didi_statments);
		tv_youbu_statments = (TextView) findViewById(R.id.tv_youbu_statments);
		tv_statments_date = (TextView) findViewById(R.id.tv_statments_date);

		tv_statments_submit = (TextView) findViewById(R.id.tv_statments_submit);

		Date date = new Date();
		Format format = new SimpleDateFormat("yyyy-MM-dd");
        tv_statments_date.setText(format.format(new Date()));
		tv_title.setText("流水管理");
	}

	@Override
	protected void setListener() {

		final Calendar calendar = Calendar.getInstance();
//		final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
//				(OnDateSetListener) this, calendar.get(Calendar.YEAR),
//				calendar.get(Calendar.MONTH),
//				calendar.get(Calendar.DAY_OF_MONTH), isVibrate());

		rl_didi_statments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(StatementsActivity.this);
				phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);

				new AlertDialog.Builder(StatementsActivity.this)
						.setView(phone)
						.setTitle("请输入滴滴金额")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (!"".equals(phone.getText()
												.toString())) {
											tv_didi_statments.setText(phone
													.getText().toString());
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});
		
		rl_youbu_statments.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText phone = new EditText(StatementsActivity.this);
				phone.setInputType(InputType.TYPE_CLASS_PHONE);
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {
						InputMethodManager inputManager = (InputMethodManager) phone
								.getContext().getSystemService(
										Context.INPUT_METHOD_SERVICE);
						inputManager.showSoftInput(phone, 0);
					}
				}, 200);

				new AlertDialog.Builder(StatementsActivity.this)
						.setView(phone)
						.setTitle("请输入滴滴金额")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (!"".equals(phone.getText()
												.toString())) {
											tv_youbu_statments.setText(phone
													.getText().toString());
										}
									}
								}).setNegativeButton("取消", null).show();
			}
		});

		rl_statments_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				datePickerDialog.setVibrate(isVibrate());
//				datePickerDialog.setYearRange(2007, 2016);
//				datePickerDialog
//						.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
//				datePickerDialog.show(getSupportFragmentManager(),
//						"Sdatepicker");
			}
		});

//		DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager()
//				.findFragmentByTag("Sdatepicker");
//		if (dpd != null) {
//			dpd.setOnDateSetListener(this);
//		}
		// 流水提交
		tv_statments_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String tv_didi_statments_str = tv_didi_statments.getText()
						.toString().trim();
				String tv_youbu_statments_str = tv_youbu_statments.getText()
						.toString().trim();
				String tv_statments_date_str = tv_statments_date.getText()
						.toString().trim();

				if (TextUtils.isEmpty(tv_didi_statments_str) && TextUtils.isEmpty(tv_youbu_statments_str)) {
					showToast("请至少输入一个平台的金额!");
					return;
				}
				if (TextUtils.isEmpty(tv_statments_date_str)) {
					showToast("请选择提交日期");
					return;
				}

				HttpRequest request_reg = new HttpRequestImpl(
						StatementsActivity.this, AppConstants.STATEMENTSUBMIT,
						null);

				request_reg
						.addParam(
								"tel",
								(String) SPUtils.getAppSpUtil().get(
										StatementsActivity.this,
										SPUtils.KEY_PHONENUM, ""))
						.addParam("didiAmount", tv_didi_statments_str)
						.addParam("youbuAmount", tv_youbu_statments_str)
						.addParam("date", tv_statments_date_str);

				getDataFromServer(request_reg, new CommCallBack(
						StatementsActivity.this, "提交流水成功",
						new CommCallBack.CallBackSucc() {
							@Override
							public void onRequestSucc(JSONObject object) {
								
								if("200".equals(object.optString("status"))){
									showToast("流水提交成功!");
								}else{
									showToast("成为通途专车司机，才可提交流水，谢谢！");
								}
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
