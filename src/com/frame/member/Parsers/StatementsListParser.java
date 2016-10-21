package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.StatementsListBean;
import com.frame.member.bean.StatementsListBean.StatementsItem;

public class StatementsListParser extends BaseParser<StatementsListBean> {

	@Override
	public StatementsListBean parseJSON(String text) throws JSONException {

		StatementsListBean result = new StatementsListBean();

		JSONObject resultObj = new JSONObject(text);
		if (resultObj != null) {
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");

			if (TAG_STATUS_SUCC.equals(result.code)) {
				JSONArray dataArray = (JSONArray)resultObj.optJSONArray("monthDetail");
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject dataObj = dataArray.optJSONObject(i);
					StatementsItem statementsItem = new StatementsItem();
					
					statementsItem.money = dataObj.optString("money");
					statementsItem.plat = dataObj.optString("platform");
					statementsItem.submit_date = dataObj
							.optString("date");
					
					result.statementsList.add(statementsItem);
				}
			}
		}

		return result;
	}

}
