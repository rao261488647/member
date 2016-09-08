package com.frame.member.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.Utils.SPUtils;

public class WelcomeActivity extends BaseActivity {
	TextView tv_login_choice,tv_register_choice;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_welcome);

	}

	@Override
	protected void findViewById() {
		tv_login_choice = (TextView) findViewById(R.id.tv_login_choice);
		tv_register_choice = (TextView) findViewById(R.id.tv_register_choice);
	}

	@Override
	protected void setListener() {
		tv_login_choice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
		
		tv_register_choice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WelcomeActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			}
		});
	}

	@Override
	protected void processLogic() {
	
		
//		toGetToken();
		// 延迟两秒后执行run方法中的页面跳转
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (!TextUtils.isEmpty((String) SPUtils.getAppSpUtil().get(WelcomeActivity.this,
						SPUtils.KEY_MEMBERUSERID, ""))) {
					goMainPage();
				} else {
//					Intent intent = new Intent(WelcomeActivity.this,
//							LoginActivity.class);
//					startActivity(intent);
//					WelcomeActivity.this.finish();
//					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					com.nineoldandroids.animation.ObjectAnimator.ofFloat(tv_login_choice, "alpha", 0.0f,1.0f)
																.setDuration(1000)
																.start();
					com.nineoldandroids.animation.ObjectAnimator.ofFloat(tv_register_choice, "alpha", 0.0f,1.0f)
					.setDuration(1000)
					.start();
					
				}
//				goMainPage();
				
			}
		}, 2000);
	}
	
//	private void toGetToken(){
//		BaseParser<TokenResult> parser =new TokenParser();
//		HttpRequestImpl request = new HttpRequestImpl(WelcomeActivity.this, "/gettoken", 
//				parser,HttpRequest.RequestMethod.post);
//		request.addParam("deviceId", CommonUtil.getDeviceIMEI(this));
//		getDataFromServer(request,false, callback);
//	}
//	private DataCallback<TokenResult> callback = new DataCallback<TokenResult>() {
//
//		@Override
//		public void processData(TokenResult object, RequestResult result) {
//			if(result == RequestResult.Success){
//				if(object != null){
//					SPUtils.getAppSpUtil().put(WelcomeActivity.this, SPUtils.KEY_TOKEN,object.token);
//					showToast("Token获得成功");
//				}
//			}
//		}
//	};

	private void goMainPage() {
		Intent intent = new Intent(WelcomeActivity.this,
				MainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}


}
