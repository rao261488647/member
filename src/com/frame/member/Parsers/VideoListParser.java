package com.frame.member.Parsers;



import java.util.List;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.frame.member.bean.MyBillBean.Consumption;
import com.frame.member.bean.VideoListBean.MainVideo;
import com.frame.member.bean.VideoListBean.VideoListResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VideoListParser extends BaseParser<VideoListResult> {

	/**
	 * 将请求接口返回的json转成javaBean，这里使用了Gson的转换方法
	 * ron
	 * 2016-07-07
	 */
	@Override
	public VideoListResult parseJSON(String json) throws JSONException {
		VideoListResult result = new VideoListResult();
		JSONObject result_obj = JSONObject.parseObject(json);
		if (json != null) {
			result.code = result_obj.getString("code");
			if("200".equals(result.code)){
				JSONObject obj_Json = JSONObject.parseObject(result_obj.getString("data"));
				Gson gson = new Gson();
				result.mainVideoList = gson.fromJson(obj_Json.getString("videos"),new TypeToken<List<MainVideo>>() {}.getType());
				
			}
			result.message = result_obj.getString("message");
		}
		return result;
	}

}
