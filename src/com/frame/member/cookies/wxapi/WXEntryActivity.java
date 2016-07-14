/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.frame.member.cookies.wxapi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.frame.member.AppConstants.AppConstants;
import com.frame.member.activity.BaseActivity;
import com.frame.member.bean.WXEntryResult;
import com.frame.member.bean.WXinfoResult;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/** 微信客户端回调activity示例 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	protected void loadViewLayout() {
		api = WXAPIFactory.createWXAPI(this, AppConstants.WX_ID, false);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void findViewById() {

	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {
		Log.i("onReq", arg0.toString());
	}

	@Override
	public void onResp(BaseResp resp) {
		Bundle arg0 = new Bundle();
		resp.toBundle(arg0);
		Log.i("WXEntryActivity", arg0.toString());
		String result;

		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = "成功";

			int ok_type = resp.getType();

			switch (ok_type) {
			case ConstantsAPI.COMMAND_SENDAUTH:
				// 发起授权成功
				String code = ((SendAuth.Resp) resp).code;
				sendGetRequest(code);
				break;
			case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
				// 发起分享成功
				break;
			}
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "拒绝";
			break;
		default:
			result = "未知";
			break;
		}
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();

		finish();
	}

	private void sendGetRequest(String code) {
		String wx_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
		final String uriAPI = String.format(wx_url, new String[] {
				AppConstants.WX_ID, AppConstants.WX_SECRET, code });
		Log.i("WXEntryActivity", "uriAPI--->" + uriAPI);
		new Thread() {
			public void run() {
				HttpGet httpRequest = new HttpGet(uriAPI);
				try {
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());

						Log.i("WXEntryActivity", "strResult--->" + strResult);
						// 这里是一个json格式的数据 需要拿里面的token id等数据到我们服务器后台进行注册流程
						WXEntryResult result = new WXEntryResult();

						JSONObject result_obj = new JSONObject(strResult);
						if (result_obj != null) {
							result.openid = result_obj.optString("openid");
							result.access_token = result_obj
									.optString("access_token");
							result.expires_in = result_obj
									.optString("expires_in");
							result.refresh_token = result_obj
									.optString("refresh_token");
							result.scope = result_obj.optString("scope");
							result.unionid = result_obj.optString("unionid");
						}
						getWXinfo(result.access_token, result.openid);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	String nickname;

	private void getWXinfo(String access_token, String openid) {
		String wx_url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
		final String uriAPI = String.format(wx_url, new String[] {
				access_token, openid });
		Log.i("WXEntryActivity", "uriAPI--->" + uriAPI);
		new Thread() {
			public void run() {
				HttpGet httpRequest = new HttpGet(uriAPI);
				try {
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String strPersonResult = EntityUtils
								.toString(httpResponse.getEntity());

						Log.i("WXEntryActivity", "strPersonResult--->"
								+ strPersonResult);
						// 这里是一个json格式的数据 需要拿里面的token id等数据到我们服务器后台进行注册流程
						final WXinfoResult result = new WXinfoResult();

						JSONObject result_obj = new JSONObject(strPersonResult);
						if (result_obj != null) {
							result.openid = result_obj.optString("openid");
							result.nickname = result_obj.optString("nickname");
							result.sex = result_obj.optString("sex");
							if ("1".equals(result.sex)) {
								result.sex = "男";
							} else if ("0".equals(result.sex)) {
								result.sex = "女";
							}
							result.province = result_obj.optString("province");
							result.city = result_obj.optString("city");
							result.country = result_obj.optString("country");
							result.headimgurl = result_obj
									.optString("headimgurl");
							result.unionid = result_obj.optString("unionid");
						}
						Log.i("WXEntryActivity", "headimgurl-->"
								+ result.headimgurl);

						try {
							nickname = new String(
									result.nickname.getBytes("iso-8859-1"),
									"utf-8");
							Log.i("WXEntryActivity", "nickname-->" + nickname);
							Log.i("WXEntryActivity", "nickname0-->"
									+ URLEncoder.encode(nickname, "utf-8"));

						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}

						// 回到主线程执行
						runOnUiThread(new Runnable() {

							@Override
							public void run() {

							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();
	}


}
