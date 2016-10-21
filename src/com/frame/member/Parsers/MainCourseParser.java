package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MainCourseBean.MainCourseBanner;
import com.frame.member.bean.MainCourseBean.MainCourseNews;
import com.frame.member.bean.MainCourseBean.MainCourseResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainCourseParser extends BaseParser<MainCourseResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public MainCourseResult parseJSON(String json) throws JSONException {
		MainCourseResult result = new MainCourseResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.mainBannerData = 
						gson.fromJson(obj_Json.getString("banner"), new TypeToken<List<MainCourseBanner>>() {}.getType());
				result.mainNewsData = 
						gson.fromJson(obj_Json.getString("teach"), new TypeToken<List<MainCourseNews>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
