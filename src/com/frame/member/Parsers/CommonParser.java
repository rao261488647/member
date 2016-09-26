package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.CommonBean;

public class CommonParser extends BaseParser<CommonBean> {

	@Override
	public CommonBean parseJSON(String text) throws JSONException {
		CommonBean result = new CommonBean();
		
		JSONObject resultObj = new JSONObject(text);
		if(resultObj != null){
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");
			
		}
		
		return result;
	}

}
