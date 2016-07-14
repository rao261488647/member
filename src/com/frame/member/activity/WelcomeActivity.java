package com.frame.member.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.frame.member.R;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.TokenParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.bean.TokenResult;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_welcome);

	}

	@Override
	protected void findViewById() {

	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {
		
		toGetToken();
		// 延迟两秒后执行run方法中的页面跳转
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				if (!TextUtils.isEmpty((String) SPUtils.getAppSpUtil().get(WelcomeActivity.this,
						SPUtils.KEY_MEMBERUSERID, ""))) {
					goMainPage();
				} else {
					Intent intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					startActivity(intent);
					WelcomeActivity.this.finish();
					overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				}
			}
		}, 2000);
	}
	
	private void toGetToken(){
		BaseParser<TokenResult> parser =new TokenParser();
		HttpRequestImpl request = new HttpRequestImpl(WelcomeActivity.this, "/gettoken", 
				parser,HttpRequest.RequestMethod.post);
		request.addParam("deviceId", CommonUtil.getDeviceIMEI(this));
		getDataFromServer(request,false, callback);
	}
	private DataCallback<TokenResult> callback = new DataCallback<TokenResult>() {

		@Override
		public void processData(TokenResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					SPUtils.getAppSpUtil().put(WelcomeActivity.this, SPUtils.KEY_TOKEN,object.token);
					showToast("Token获得成功");
				}
			}
		}
	};

	private void goMainPage() {
		Intent intent = new Intent(WelcomeActivity.this,
				MainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
	}


}
