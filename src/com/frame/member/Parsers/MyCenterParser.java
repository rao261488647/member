package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MyCenterBean.Menu;
import com.frame.member.bean.MyCenterBean.MyCenterResult;
import com.frame.member.bean.MyCenterBean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyCenterParser extends BaseParser<MyCenterResult> {

	
	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-09-18
	 */
	@Override
	public MyCenterResult parseJSON(String json) throws JSONException {
		MyCenterResult result = new MyCenterResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.user = gson.fromJson(obj_Json.getString("user"),User.class);
				result.menuList = 
						gson.fromJson(obj_Json.getString("menu"), new TypeToken<List<Menu>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
