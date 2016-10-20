package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MainEssenceBean.EssenceInfo;
import com.frame.member.bean.MainEssenceBean.EssenceResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainEssenceParser extends BaseParser<EssenceResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public EssenceResult parseJSON(String json) throws JSONException {
		EssenceResult result = new EssenceResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.essenceInfoList = gson.fromJson(obj_Json.getString("best"),new TypeToken<List<EssenceInfo>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
