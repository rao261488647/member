package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MainInfoBean.MainBanner;
import com.frame.member.bean.MainInfoBean.MainInfoResult;
import com.frame.member.bean.MainInfoBean.MainNews;
import com.frame.member.bean.MainInfoBean.MainRemmendClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainInfoParser extends BaseParser<MainInfoResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MainInfoResult parseJSON(String json) throws JSONException {
		MainInfoResult result = new MainInfoResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.mainBannerData = 
						gson.fromJson(obj_Json.getString("banner"), new TypeToken<List<MainBanner>>() {}.getType());
				result.mainRemmendClassData = 
						gson.fromJson(obj_Json.getString("remmendClass"), new TypeToken<List<MainRemmendClass>>() {}.getType());
				result.mainNewsData = 
						gson.fromJson(obj_Json.getString("news"), new TypeToken<List<MainNews>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
