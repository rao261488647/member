package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.BaseBean;

public class NoBackParser extends BaseParser<BaseBean> {

	@Override
	public BaseBean parseJSON(String json) throws JSONException {
		BaseBean result = new BaseBean();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			if(!"200".equals(result.code))
			result.message = result_obj.optString("message");
		}
		return result;
	}

}
