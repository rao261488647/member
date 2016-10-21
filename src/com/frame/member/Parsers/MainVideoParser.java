package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MainInfoBean.MainBanner;
import com.frame.member.bean.MainInfoBean.MainNews;
import com.frame.member.bean.MainInfoBean.MainRemmendClass;
import com.frame.member.bean.MainVideoBean.MainVideoCategory;
import com.frame.member.bean.MainVideoBean.MainVideoResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainVideoParser extends BaseParser<MainVideoResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MainVideoResult parseJSON(String json) throws JSONException {
		MainVideoResult result = new MainVideoResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.mainVideoCategoryData = 
						gson.fromJson(obj_Json.getString("categorys"), new TypeToken<List<MainVideoCategory>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
