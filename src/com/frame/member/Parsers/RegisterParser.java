package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.LoginCodeResult;
import com.frame.member.bean.RegisterResult;

public class RegisterParser extends BaseParser<RegisterResult> {

	@Override
	public RegisterResult parseJSON(String json) throws JSONException {
		RegisterResult result = new RegisterResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
				result.token = obj_Json.optString("token");
				result.mobile = obj_Json.optString("mobile");
				result.memberUserId = obj_Json.optInt("memberUserId");
			}
			result.message = result_obj.optString("message");
		}
		return result;
	}

}
