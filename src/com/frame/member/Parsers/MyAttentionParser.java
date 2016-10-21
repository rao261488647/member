package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.MyAttentionResult;

public class MyAttentionParser extends BaseParser<List<MyAttentionResult>> {
//	{
//	"code": 200,
//	"message": "返回数据成功",
//	-
//	"data": {
//	"myfollow_num": "7",
//	"myfollow_friends": [
//	-
//	{
//	"appHeadThumbnail": "1.jpg"
//	},
//	-
//	
//	]
//	}
//	}
	@Override
	public List<MyAttentionResult> parseJSON(String json) throws JSONException {
		List<MyAttentionResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj_json = result_obj.optJSONObject("data");
			if(obj_json != null){
				JSONArray arr = obj_json.optJSONArray("myfollow_friends");
				if(arr != null && arr.length() > 0){
					list_result = new ArrayList<MyAttentionResult>();
					for(int i =0;i <arr.length(); i++){
						JSONObject json_result = arr.optJSONObject(i);
						if(json_result != null){
							MyAttentionResult result = new MyAttentionResult();
							result.code = result_obj.optString("code");
							result.message = result_obj.optString("message");
							result.myfollow_num = obj_json.optString("myfollow_num");
							result.appHeadThumbnail = json_result.optString("appHeadThumbnail");
							list_result.add(result);
							result = null;
						}
					}
					
				}
			}
		}
		return list_result;
	}

}
