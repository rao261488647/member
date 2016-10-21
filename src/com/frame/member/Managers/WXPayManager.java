package com.frame.member.Managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.frame.member.bean.IWXUtil;
import com.frame.member.bean.MD5;
import com.frame.member.bean.PayVo;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayManager {
	private static final String WX_PAY_ID = "wxb1a56b0e36de95bb";
	private static final String WX_PAY_SECRET = "96764440ed1a9e52878909588f79b615";
	
	public static final String TAG = "WechatpayUtil";
	private IWXAPI api;
	Context context;
	private PayVo payVo;

	public WXPayManager(Context context) {
		api = WXAPIFactory.createWXAPI(context, "wxb1a56b0e36de95bb", true);
		this.context = context;
	}

	public void payProcessByWechat(PayVo payVo) {
		if (payVo == null)
			return;
		this.payVo = payVo;
		// 检查手机微信客户端是否支持支付功能
		boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
		if (!isPaySupported) {
			Toast.makeText(context, "请更新手机微信客户端后重试", Toast.LENGTH_SHORT).show();
			return;
		}
		// 获取token
		new GetAccessTokenTask().execute();
	}

	private class GetAccessTokenTask extends
			AsyncTask<Void, Void, GetAccessTokenResult> {

		@Override
		protected void onPostExecute(GetAccessTokenResult result) {
			if (result.localRetCode == LocalRetCode.ERR_OK) {
				GetPrepayIdTask getPrepayId = new GetPrepayIdTask(
						result.accessToken);
				getPrepayId.execute();
			} else {
				Toast.makeText(context, "联接微信失败，请稍后重试", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected GetAccessTokenResult doInBackground(Void... params) {
			GetAccessTokenResult result = new GetAccessTokenResult();

			String url = String
					.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
							WX_PAY_ID,
							WX_PAY_SECRET);
			// Util.Debug(TAG, "get access token, url = " + url);

			byte[] buf = IWXUtil.httpGet(url);
			if (buf == null || buf.length == 0) {
				result.localRetCode = LocalRetCode.ERR_HTTP;
				return result;
			}

			String content = new String(buf);
			result.parseFrom(content);
			return result;
		}
	}

	private void sendPayReq(GetPrepayIdResult result) {
		PayReq req = new PayReq();
		req.appId = WX_PAY_ID;
		req.partnerId = "1219635201";
		req.prepayId = result.prepayId;
		req.nonceStr = nonceStr;
		req.timeStamp = String.valueOf(timeStamp);
		req.packageValue = "Sign=" + packageValue;

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("appkey", APP_KEY));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		req.sign = genSign(signParams);

		// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.registerApp("wxbf023b3eb2f99944");
		api.sendReq(req);
	}

	private class GetPrepayIdTask extends
			AsyncTask<Void, Void, GetPrepayIdResult> {

		private String accessToken;

		public GetPrepayIdTask(String accessToken) {
			this.accessToken = accessToken;
		}

		@Override
		protected void onPostExecute(GetPrepayIdResult result) {

			if (result.localRetCode == LocalRetCode.ERR_OK) {
				sendPayReq(result);
			} else {
				Toast.makeText(context, "获取加密订单号失败", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected GetPrepayIdResult doInBackground(Void... params) {

			String url = String.format(
					"https://api.weixin.qq.com/pay/genprepay?access_token=%s",
					accessToken);
			String entity = genProductArgs();

			// MyLog.d(TAG, "doInBackground, url = " + url);
			// MyLog.d(TAG, "doInBackground, entity = " + entity);

			GetPrepayIdResult result = new GetPrepayIdResult();

			byte[] buf = IWXUtil.httpPost(url, entity);
			if (buf == null || buf.length == 0) {
				result.localRetCode = LocalRetCode.ERR_HTTP;
				return result;
			}

			String content = new String(buf);
			result.parseFrom(content);
			return result;
		}
	}

	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	private String getTraceId() {
		return "crestxu_" + genTimeStamp();
	}

	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	private long timeStamp;
	private String nonceStr, packageValue;

	// out_trade_no, traceId,
	// signed_params, accessToken;

	private String genProductArgs() {
		JSONObject json = new JSONObject();

		try {
			json.put("appid", "wxbf023b3eb2f99944");
			String traceId = getTraceId(); // traceId
											// 由开发者自定义，可用于订单的查询与跟踪，建议根据支付用户信息生成此id
			json.put("traceid", traceId);
			nonceStr = genNonceStr();
			json.put("noncestr", nonceStr);

			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("bank_type", "WX"));
			packageParams.add(new BasicNameValuePair("body", payVo.subject));// 商品名
			packageParams.add(new BasicNameValuePair("fee_type", "1"));
			packageParams.add(new BasicNameValuePair("input_charset", "UTF-8"));
			//TODO 回调网址
//			packageParams.add(new BasicNameValuePair("notify_url",
//					HttpRequestImpl.URL + "payNotifyUrl.jsp"));
			packageParams
					.add(new BasicNameValuePair("out_trade_no", payVo.requestid));
			packageParams.add(new BasicNameValuePair("partner", "1219635201"));
			packageParams.add(new BasicNameValuePair("spbill_create_ip",
					"196.168.1.1"));
//			packageParams.add(new BasicNameValuePair("total_fee", String
//					.valueOf((int)(payVo.price * payVo.num)))); // 总价格
			packageParams.add(new BasicNameValuePair("total_fee", String
					.valueOf((int)(payVo.price * payVo.num * 100)))); // 总价格

			packageValue = genPackage(packageParams);

			json.put("package", packageValue);
			timeStamp = genTimeStamp();
			json.put("timestamp", timeStamp);

			List<NameValuePair> signParams = new LinkedList<NameValuePair>();
			signParams
					.add(new BasicNameValuePair("appid", "wxbf023b3eb2f99944"));
			signParams.add(new BasicNameValuePair("appkey", APP_KEY));
			signParams.add(new BasicNameValuePair("noncestr", nonceStr));
			signParams.add(new BasicNameValuePair("package", packageValue));
			signParams.add(new BasicNameValuePair("timestamp", String
					.valueOf(timeStamp)));
			signParams.add(new BasicNameValuePair("traceid", traceId));
			json.put("app_signature", genSign(signParams));

			json.put("sign_method", "sha1");
		} catch (Exception e) {
			Log.e(TAG, "genProductArgs fail, ex = " + e.getMessage());
			return null;
		}

		return json.toString();
	}

	private String genSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		int i = 0;
		for (; i < params.size() - 1; i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append(params.get(i).getName());
		sb.append('=');
		sb.append(params.get(i).getValue());

		String sha1 = IWXUtil.sha1(sb.toString());
		// MyLog.d(TAG, "genSign, sha1 = " + sha1);
		return sha1;
	}

	private static final String APP_KEY = "gXfOJvmtjAi9xO1lpy8O3saZDbdpmlDrcWAvdXxletJcBjRElPUu9UEpoyofNISlB53I3VVFOCwi2FWsEG85gcHnI1LTl3PfkQhqBcZf1D4xkMLjURgrsoXtUNAaH4cg";
	private static final String PARTNER_KEY = "5e54ec1e9bd0661abd1ad612b1bc4a88";

	private String genPackage(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(PARTNER_KEY); // 注意：不能hardcode在客户端，建议genPackage这个过程都由服务器端完成

		// 进行md5摘要前，params内容为原始内容，未经过url encode处理
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes())
				.toUpperCase();

		return URLEncodedUtils.format(params, "utf-8") + "&sign=" + packageSign;
	}

	private static class GetPrepayIdResult {

		private static final String TAG = "MicroMsg.SDKSample.PayActivity.GetPrepayIdResult";

		public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
		public String prepayId;
		public int errCode;
		public String errMsg;

		public void parseFrom(String content) {

			if (content == null || content.length() <= 0) {
				Log.e(TAG, "parseFrom fail, content is null");
				localRetCode = LocalRetCode.ERR_JSON;
				return;
			}

			try {
				JSONObject json = new JSONObject(content);
				if (json.has("prepayid")) { // success case
					prepayId = json.getString("prepayid");
					localRetCode = LocalRetCode.ERR_OK;
				} else {
					localRetCode = LocalRetCode.ERR_JSON;
				}

				errCode = json.getInt("errcode");
				errMsg = json.getString("errmsg");

			} catch (Exception e) {
				localRetCode = LocalRetCode.ERR_JSON;
			}
		}

		@Override
		public String toString() {
			return "GetPrepayIdResult [localRetCode=" + localRetCode
					+ ", prepayId=" + prepayId + ", errCode=" + errCode
					+ ", errMsg=" + errMsg + "]";
		}
	}

	private static enum LocalRetCode {
		ERR_OK, ERR_HTTP, ERR_JSON, ERR_OTHER
	}

	private static class GetAccessTokenResult {

		public LocalRetCode localRetCode = LocalRetCode.ERR_OTHER;
		public String accessToken;
		public int expiresIn;
		public int errCode;
		public String errMsg;

		public void parseFrom(String content) {

			if (content == null || content.length() <= 0) {
				Log.e(TAG, "parseFrom fail, content is null");
				localRetCode = LocalRetCode.ERR_JSON;
				return;
			}

			try {
				JSONObject json = new JSONObject(content);
				if (json.has("access_token")) { // success case
					accessToken = json.getString("access_token");
					expiresIn = json.getInt("expires_in");
					localRetCode = LocalRetCode.ERR_OK;
				} else {
					errCode = json.getInt("errcode");
					errMsg = json.getString("errmsg");
					localRetCode = LocalRetCode.ERR_JSON;
				}

			} catch (Exception e) {
				localRetCode = LocalRetCode.ERR_JSON;
			}
		}

		@Override
		public String toString() {
			return "GetAccessTokenResult [localRetCode=" + localRetCode
					+ ", accessToken=" + accessToken + ", expiresIn="
					+ expiresIn + ", errCode=" + errCode + ", errMsg=" + errMsg
					+ "]";
		}
	}
}
