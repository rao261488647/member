package com.frame.member.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;

public class CommCallBack implements DataCallback<String> {

	private Context context;
	private String tips;

	private CallBackSucc listener;

	public interface CallBackSucc {
		void onRequestSucc(JSONObject object);
	}

	public CommCallBack(Context context, String tips) {
		this.context = context;
		this.tips = tips;
	}

	public CommCallBack(Context context, String tips, CallBackSucc listener) {
		this.context = context;
		this.tips = tips;
		this.listener = listener;
	}

	@Override
	public void processData(String object, RequestResult result) { // 这里很奇怪 点击通知条目会回调两次
		if (TextUtils.isEmpty(object)) {
//			showToast("请求失败，请稍后重试");
			return;
		}

		MyLog.d(tips, object);

		try {
			JSONObject resultObj = new JSONObject(object);
			if (resultObj != null) {
				switch (resultObj.optInt("status")) {
				case 200:
					showToast(tips);
					if (listener != null)
						listener.onRequestSucc(resultObj);
					break;
				default:
					String errormsg = resultObj.optString("errormsg");
					if (!TextUtils.isEmpty(errormsg))
						showToast(errormsg);
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void showToast(String tip) {
		if (TextUtils.isEmpty(tip))
			return;
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}
}
