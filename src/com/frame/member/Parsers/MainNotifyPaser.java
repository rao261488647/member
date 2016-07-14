package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.MainNotifyBean;
import com.frame.member.bean.MainNotifyBean.Notify;

public class MainNotifyPaser extends BaseParser<MainNotifyBean> {

	@Override
	public MainNotifyBean parseJSON(String text) throws JSONException {
		MainNotifyBean result = new MainNotifyBean();
		
		JSONObject resultObj = new JSONObject(text);
		if(resultObj != null){
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");
			result.totoalRecord = resultObj.optInt("totoalRecord");
			
			JSONArray dataArray = resultObj.optJSONArray("data");
			if(dataArray != null && dataArray.length() > 0){
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject dataObj = dataArray.optJSONObject(i);
					Notify notify = new Notify();
					notify.name = dataObj.optString("name");
					notify.detail = dataObj.optString("detail");
					notify.date = dataObj.optString("date");
					result.mainpage_data.add(notify);
					notify = null;
				}
			}
			
			JSONArray urlArray = resultObj.optJSONArray("url");
			if(urlArray != null && urlArray.length() > 0){
				for (int i = 0; i < urlArray.length(); i++) {
					JSONObject urlObj = urlArray.optJSONObject(i);
					String url = urlObj.optString("url");
					result.mainpage_urls.add(url);
					url = null;
				}
			}
		}
		
		return result;
	}

}
