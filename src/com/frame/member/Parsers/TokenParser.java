package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.TokenResult;

public class TokenParser extends BaseParser<TokenResult> {

	@Override
	public TokenResult parseJSON(String json) throws JSONException {
		TokenResult result = new TokenResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
				result.token = obj_Json.optString("token");
			}
			
		}
		return result;
	}

}
