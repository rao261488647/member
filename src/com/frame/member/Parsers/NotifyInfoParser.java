package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.NotifyBean;
import com.frame.member.bean.NotifyListBean;

public class NotifyInfoParser extends BaseParser<NotifyListBean> {

	@Override
	public NotifyListBean parseJSON(String text) throws JSONException {
		NotifyListBean result = new NotifyListBean();

		JSONObject resultObj = new JSONObject(text);
		if (resultObj != null) {
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");

			JSONArray dataArray = resultObj.optJSONArray("data");

			if (dataArray != null && dataArray.length() > 0) {
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject obj = dataArray.optJSONObject(i);

					NotifyBean notifyBean = new NotifyBean();
					notifyBean.type = obj.optString("type");
					notifyBean.amount = obj.optString("amount");
					notifyBean.amountMonth = obj.optString("amountMonth");
					notifyBean.sendDate = obj.optString("sendDate");
					notifyBean.detail = obj.optString("detail");
					notifyBean.noticeId = obj.optString("noticeId");
					notifyBean.isReaded = obj.optString("isReaded");
					
					result.notifyList.add(notifyBean);
					
					notifyBean = null;
				}
			}
			
		}

		return result;
	}

}
