package com.frame.member.Parsers;



import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MyBaseInfoBean.MyBaseInfoResult;
import com.frame.member.bean.MyBaseInfoBean.UserInfo;
import com.google.gson.Gson;

public class MyBaseInfoParser extends BaseParser<MyBaseInfoResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-09-18
	 */
	@Override
	public MyBaseInfoResult parseJSON(String json) throws JSONException {
		MyBaseInfoResult result = new MyBaseInfoResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.userInfo = gson.fromJson(obj_Json.getString("user"),UserInfo.class);
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
