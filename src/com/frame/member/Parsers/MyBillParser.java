package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MainCourseBean.MainCourseNews;
import com.frame.member.bean.MyBillBean.Consumption;
import com.frame.member.bean.MyBillBean.MyBillResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyBillParser extends BaseParser<MyBillResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MyBillResult parseJSON(String json) throws JSONException {
		MyBillResult result = new MyBillResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.consumptionList = gson.fromJson(obj_Json.getString("consumption"),new TypeToken<List<Consumption>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
