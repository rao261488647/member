package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MyCollectBean.CollectClass;
import com.frame.member.bean.MyCollectBean.MyCollectClassResult;
import com.frame.member.bean.MyMsgBean.Zan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyCollectClassParser extends BaseParser<MyCollectClassResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MyCollectClassResult parseJSON(String json) throws JSONException {
		MyCollectClassResult result = new MyCollectClassResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.collectClassList = 
						gson.fromJson(obj_Json.getString("class"), new TypeToken<List<CollectClass>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
