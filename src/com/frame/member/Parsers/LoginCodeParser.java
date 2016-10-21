package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.LoginCodeResult;

public class LoginCodeParser extends BaseParser<LoginCodeResult> {

	@Override
	public LoginCodeResult parseJSON(String json) throws JSONException {
		LoginCodeResult result = new LoginCodeResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
//			if("200".equals(result.code)){
//				JSONObject obj_Json = result_obj.optJSONObject("data");
//				result.verificationCode = obj_Json.optString("verificationCode");
//			}
			result.message = result_obj.optString("message");
		}
		return result;
	}

}
