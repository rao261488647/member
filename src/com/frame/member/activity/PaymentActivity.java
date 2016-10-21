package com.frame.member.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Managers.AlipayManager;
import com.frame.member.Managers.PayResult;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.MyLog;
import com.frame.member.Utils.SPUtils;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends BaseActivity implements OnClickListener {
	public static final int RQF_PAY = 1;

	private TextView tv_pay_commit;

	// private PayVo payVo;

	private IWXAPI api;

	/*
	 * 支付宝支付结果回调
	 */
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AlipayManager.SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 */
//				String resultInfo = payResult.getResult();// 同步返回需要验证的信息

				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档

				MyLog.i(TT_TAG, resultStatus);
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PaymentActivity.this, "支付宝支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非"9000"则代表可能支付失败
					// "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PaymentActivity.this, "支付宝支付结果确认中", Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PaymentActivity.this, "支付宝支付失败", Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case AlipayManager.SDK_CHECK_FLAG: {
				Toast.makeText(PaymentActivity.this, "支付宝检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			}
		};
	};

	private RelativeLayout rl_pay_alipay, rl_pay_wx;
	private CheckBox cb_pay_alipay, cb_pay_wx;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_pay_detail);
	}

	@Override
	protected void findViewById() {
		tv_pay_commit = (TextView) findViewById(R.id.tv_pay_commit);
		rl_pay_alipay = (RelativeLayout) findViewById(R.id.rl_pay_alipay);
		rl_pay_wx = (RelativeLayout) findViewById(R.id.rl_pay_wx);
		cb_pay_alipay = (CheckBox) findViewById(R.id.cb_pay_alipay);
		cb_pay_wx = (CheckBox) findViewById(R.id.cb_pay_wx);
	}

	@Override
	protected void setListener() {
		tv_pay_commit.setOnClickListener(this);
		rl_pay_alipay.setOnClickListener(this);
		rl_pay_wx.setOnClickListener(this);
		cb_pay_alipay.setOnClickListener(this);
		cb_pay_wx.setOnClickListener(this);
	}

	@Override
	protected void processLogic() {
		tv_title.setText("缴费");
		iv_title_back.setVisibility(0);

		api = WXAPIFactory.createWXAPI(this, "wxb1a56b0e36de95bb");
		api.registerApp("wxb1a56b0e36de95bb");

		// registerReceiver(receiver, new IntentFilter("FEEDBACK_WX_PAY"));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// unregisterReceiver(receiver);
	}

	// private BroadcastReceiver receiver = new BroadcastReceiver() {
	//
	// @Override
	// public void onReceive(Context context, Intent intent) {
	// if ("FEEDBACK_WX_PAY".equals(intent.getAction())) {
	// int resp_wx_pay_code = intent.getIntExtra("resp_errorcode", -1);
	// Log.i("resp_errorcode", resp_wx_pay_code + "");
	// if (resp_wx_pay_code == 0) {
	// //TODO 上报服务器支付成功
	// paySuccess();
	// } else {
	// //TODO 上报服务器支付失败
	// payFailed();
	// }
	// }
	// }
	// };

	/**
	 * 支付成功
	 */

	private void paySuccess() {

	}

	/**
	 * 支付失败
	 */
	private void payFailed() {

	}

	private static final String PAY_TYPE_ALIPAY = "1";
	private static final String PAY_TYPE_WX = "2";

	@Override
	public void onClick(View v) {
		String payType = "";
		switch (v.getId()) {
		case R.id.tv_pay_commit:
			if (cb_pay_alipay.isChecked())
				payType = PAY_TYPE_ALIPAY;
			else if (cb_pay_wx.isChecked())
				payType = PAY_TYPE_WX;

			if (TextUtils.isEmpty(payType)) {
				showToast("请选择缴费方式");
				return;
			}

			requestServerPayInfo(payType);
			break;
		case R.id.rl_pay_alipay:
			if (cb_pay_alipay.isChecked()) {
				cb_pay_alipay.setChecked(false);
			} else {
				cb_pay_alipay.setChecked(true);
				if (cb_pay_wx.isChecked())
					cb_pay_wx.setChecked(false);

			}
			break;
		case R.id.cb_pay_alipay:
			if (cb_pay_alipay.isChecked() && cb_pay_wx.isChecked())
				cb_pay_wx.setChecked(false);
			break;
		case R.id.rl_pay_wx:
			if (cb_pay_wx.isChecked()) {
				cb_pay_wx.setChecked(false);
			} else {
				cb_pay_wx.setChecked(true);
				if (cb_pay_alipay.isChecked())
					cb_pay_alipay.setChecked(false);
			}
			break;
		case R.id.cb_pay_wx:
			if (cb_pay_alipay.isChecked() && cb_pay_wx.isChecked())
				cb_pay_alipay.setChecked(false);
			break;
		}
	}

	/**
	 * 这个方法去我们自己服务器请求订单信息 然后在请求回来之后开始发起支付
	 * 
	 */
	private void requestServerPayInfo(final String payType) {
		HttpRequest request_pay_info = new HttpRequestImpl(PaymentActivity.this, AppConstants.PAYFEESUBMIT, null);

		request_pay_info
				.addParam("cell", (String) SPUtils.getAppSpUtil().get(PaymentActivity.this, SPUtils.KEY_PHONENUM, ""))
				.addParam("month", "6").addParam("payType", payType) // (1是支付宝，2是微信)
				.addParam("money", "0.01").addParam("remark", "这里是备注").addParam("Type", "租金"); // 押金、租金、其他付款

		getDataFromServer(request_pay_info, new DataCallback<String>() {

			@Override
			public void processData(String object, RequestResult result) {
				if (result == RequestResult.Success) {
					MyLog.i(TT_TAG, "支付返回信息----》" + object);

					try {
						JSONObject resultObj = new JSONObject(object);
						String status = resultObj.optString("status");
						if ("200".equals(status)) {
							if (PAY_TYPE_ALIPAY.equals(payType))
								logicPayAlipay(resultObj);
							else if (PAY_TYPE_WX.equals(payType))
								logicPayWX(resultObj);
						} else
							showToast("订单生成失败，请稍后重试");

					} catch (JSONException e) {
						showToast("订单生成失败，请稍后重试");
						e.printStackTrace();
					}

				}
			}
		}, "生成订单信息...");

	}

	private void logicPayAlipay(JSONObject resultObj) {

		String payInfo = resultObj.optString("payinfo");

		new AlipayManager(PaymentActivity.this).pay(mHandler, payInfo);
	}

	private void logicPayWX(JSONObject json) {
		JSONObject payinfoObj = json.optJSONObject("payinfo");
		if (payinfoObj != null) {
			JSONObject datasObj = payinfoObj.optJSONObject("dates");
			boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
			if (isPaySupported) {
				PayReq req = new PayReq(); // 用后台返回数据拼装这个对象数据
				req.appId = datasObj.optString("appid");
				req.partnerId = datasObj.optString("partnerid");
				req.prepayId = datasObj.optString("prepayid");
				req.nonceStr = datasObj.optString("noncestr");
				req.timeStamp = datasObj.optString("timestamp");
				req.packageValue = datasObj.optString("packages");
				req.sign = datasObj.optString("sign");
				req.extData = datasObj.optString("extData");
				; // optional
				api.registerApp("wxb1a56b0e36de95bb");
				api.sendReq(req);

			} else {
				showToast("您的微信版本过低 请升级后重试");
			}
		}

	}
}
