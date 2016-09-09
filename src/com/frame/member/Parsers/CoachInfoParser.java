package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachInfoResult;

public class CoachInfoParser extends BaseParser<CoachInfoResult> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	"data": {
//	"coach": {
//	"coachId": "13",
//	"coachName": "张亚东",
//	"headImg": "13_78521447126621.jpg",
//	"coachIdentity": "1",
//	"istutor": "0",
//	"levelName": "五级指导员",
//	"titleName": "联盟高级",
//	"coachSign": ""
//	},
//	"followCoach": "0"
//	}
//	}
	@Override
	public CoachInfoResult parseJSON(String json) throws JSONException {
		CoachInfoResult result = new CoachInfoResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			JSONObject obj_json = result_obj.optJSONObject("data");
			if(obj_json != null){
				JSONObject obj = obj_json.optJSONObject("coach");
				result.coachId = obj.optString("coachId");
				result.headImg = obj.optString("headImg");
				result.coachName = obj.optString("coachName");
				result.coachIdentity = obj.optString("coachIdentity");
				result.istutor = obj.optString("istutor");
				result.levelName = obj.optString("levelName");
				result.titleName = obj.optString("titleName");
				result.coachSign = obj.optString("coachSign");
				result.followCoach = obj_json.optString("followCoach");
				
			}
		}
		return result;
	}

}
