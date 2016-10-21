package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.WXinfoResult;

public class WXinfoParser extends BaseParser<WXinfoResult> {
//	{ 
//	"openid":"OPENID",
//	"nickname":"NICKNAME",
//	"sex":1,
//	"province":"PROVINCE",
//	"city":"CITY",
//	"country":"COUNTRY",
//	"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//	"privilege":[
//	"PRIVILEGE1", 
//	"PRIVILEGE2"
//	],
//	"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//
//	}
	@Override
	public WXinfoResult parseJSON(String json) throws JSONException {
		WXinfoResult result = new WXinfoResult();

		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.openid = result_obj.optString("openid");
			result.nickname = result_obj.optString("nickname");
			result.sex = result_obj.optString("sex");
			result.province = result_obj.optString("province");
			result.city = result_obj.optString("city");
			result.country = result_obj.optString("country");
			result.headimgurl = result_obj.optString("headimgurl");
			result.unionid = result_obj.optString("unionid");
		}

		return result;
	}
}
