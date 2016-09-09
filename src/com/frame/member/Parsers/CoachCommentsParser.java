package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachCommentsResult;

public class CoachCommentsParser extends BaseParser<List<CoachCommentsResult>> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	-
//	"data": {
//	"coaches": [
//	-
//	{
//	"coachId": "5",
//	"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//	"coachName": "王云龙",
//	"levelName": "五级指导员",
//	"commentContent": "好神奇的学员，超出我多年来的教学经验",
//	"commentTime": "2016-07-05 10:52:23",
//	"praiseNum": "19",
//	"followCoach": "0"
//	"praiseCoach": "0"
//	}
//	]
//	}
//	}
	@Override
	public List<CoachCommentsResult> parseJSON(String json) throws JSONException {
		List<CoachCommentsResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj = result_obj.optJSONObject("data");
			if(obj != null){
				JSONArray arr = obj.optJSONArray("coaches");
				if(arr != null && arr.length() > 0){
					list_result = new ArrayList<CoachCommentsResult>();
					for(int i=0; i<arr.length();i++){
						JSONObject obj_coach = arr.optJSONObject(i);
						CoachCommentsResult result = new CoachCommentsResult();
						result.coachId = obj_coach.optString("coachId");
						result.headImg = obj_coach.optString("headImg");
						result.coachName = obj_coach.optString("coachName");
						result.levelName = obj_coach.optString("levelName");
						result.commentContent = obj_coach.optString("commentContent");
						result.commentTime = obj_coach.optString("commentTime");
						result.praiseNum = obj_coach.optString("praiseNum");
						result.followCoach = obj_coach.optString("followCoach");
						result.praiseCoach = obj_coach.optString("praiseCoach");
						list_result.add(result);
						result = null;
					}
				}
			}
		}
		return list_result;
	}

}
