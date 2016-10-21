package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MyOrderBean.MyOrder;
import com.frame.member.bean.MyOrderBean.MyOrderResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyOrderParser extends BaseParser<MyOrderResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MyOrderResult parseJSON(String json) throws JSONException {
		MyOrderResult result = new MyOrderResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.orderList = 
						gson.fromJson(obj_Json.getString("allOrders"), new TypeToken<List<MyOrder>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
