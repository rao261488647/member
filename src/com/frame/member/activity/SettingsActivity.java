package com.frame.member.activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置
 * Ron
 */
import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Managers.AppManager;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.NoBackParser;
import com.frame.member.Utils.DataCleanManager;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.BaseBean;

public class SettingsActivity extends BaseActivity implements OnClickListener {
	
	private TextView tv_login_out;
	private TextView tv_setting_clear_cache;
	private RelativeLayout rl_clear_cache,rl_phone_me,rl_feedback;
	private String cache;
	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_settings);
	}

	@Override
	protected void findViewById() {
	  	//lv_statemetns_list = (ListView) findViewById(R.id.lv_statements_list);
		tv_login_out = (TextView) findViewById(R.id.tv_login_out);
		tv_setting_clear_cache = (TextView) findViewById(R.id.tv_setting_clear_cache);
		rl_clear_cache = (RelativeLayout) findViewById(R.id.rl_clear_cache);
		rl_phone_me = (RelativeLayout) findViewById(R.id.rl_phone_me);
		rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
		
	}
	
	@Override
	protected void setListener() {
		tv_login_out.setOnClickListener(this);
		rl_clear_cache.setOnClickListener(this);
		rl_phone_me.setOnClickListener(this);
		rl_feedback.setOnClickListener(this);

	}

	@Override
	protected void processLogic() {
		tv_title.setText("设置");
		iv_title_back.setVisibility(0);
		try {
			tv_setting_clear_cache.setText(DataCleanManager.getTotalCacheSize(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.my_text2:
			Intent intent = new Intent(this, MyInfoActivity.class);
			this.startActivity(intent);
			break;
		case R.id.tv_login_out:
			dialogLoginOut();
			break;
		case R.id.rl_clear_cache:
			if(!"0KB".equals(tv_setting_clear_cache.getText().toString())){
				dialogCache();
			}
			break;
		case R.id.rl_phone_me:
			dialPhoneNumber("400-811-7555");
			break;
		case R.id.rl_feedback:
			showToast("暂不开放");
			break;

		}
	}
	//退出登录
	private void LoginOut(){
		BaseParser<BaseBean> parser = new NoBackParser();
		HttpRequestImpl request = new HttpRequestImpl(this,
				AppConstants.APP_SORT_STUDENT+"/applogout", parser);
		request.addParam("token", (String) SPUtils.getAppSpUtil().get(this, SPUtils.KEY_TOKEN, ""))
				.addParam("memberUserId", (String) SPUtils.getAppSpUtil().get(
						this, SPUtils.KEY_MEMBERUSERID, ""));
		DataCallback<BaseBean> callback = new DataCallback<BaseBean>() {

			@Override
			public void processData(BaseBean object, RequestResult result) {
				if(object != null){
					showToast(object.message);
					SPUtils.getAppSpUtil().clear(SettingsActivity.this);
					AppManager.getAppManager().finishAllActivity();
					startActivity(new Intent(SettingsActivity.this, WelcomeActivity.class));
				}
			}
		};
		getDataFromServer(request, callback);
	}
	//登出按钮弹出窗口
	private void dialogLoginOut(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("是否要退出登录？");
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LoginOut();
			}

			
		});
		builder.create().show();
	}
	//清除缓存弹出的窗口
	private void dialogCache() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("是否要清除缓存？");
		builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 清除本应用缓存
				DataCleanManager.clearAllCache(SettingsActivity.this);
				DataCleanManager.cleanFiles(SettingsActivity.this);
				dialog.dismiss();

				final ProgressDialog progressDialog = new ProgressDialog(
						SettingsActivity.this);
				progressDialog.setProgressStyle(1);
				progressDialog.setMessage("请稍等。。");
				progressDialog.setCancelable(true);
				progressDialog.show();

				new Handler().post(new Runnable() {

					@Override
					public void run() {
						try {
							if ("0KB".equals(DataCleanManager
									.getTotalCacheSize(SettingsActivity.this))) {
								progressDialog.dismiss();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				try {
					cache = DataCleanManager
							.getTotalCacheSize(SettingsActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tv_setting_clear_cache.setText(cache);

			}
		});
		builder.create().show();
	}
	//调用电话页面
	public void dialPhoneNumber(String phoneNumber) {
	    Intent intent = new Intent(Intent.ACTION_DIAL);
	    intent.setData(Uri.parse("tel:" + phoneNumber));
	    if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}

}
