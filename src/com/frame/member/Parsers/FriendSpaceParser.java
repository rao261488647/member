package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.FriendSpaceResult;

public class FriendSpaceParser extends BaseParser<List<FriendSpaceResult>> {
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-"data": {
//		-"videos": [
//		-{
//		"subjectId": "2",
//		"subjectName": "看我飞起来没有",
//		"videoPhoto": "http://www.baidu.com",
//		"videoFileId": "qcloud--VIDEOID",
//		"videoUrl": "http://www.gaga.com",
//		"videoCode": "playcode",
//		"sendTime": "2016-07-04 00:00:00",
//		"praiseNum": "7",
//		"commentNum": "0",
//		"followAuthor": "1",
//		"coachComment": "0",
//		"praiseAuthor": "1"
//		}
//		]
//		}
//		}
	@Override
	public List<FriendSpaceResult> parseJSON(String json) throws JSONException {
		List<FriendSpaceResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj = result_obj.optJSONObject("data");
			if(obj != null){
				JSONArray arr = obj.optJSONArray("videos");
				if(arr != null && arr.length() > 0){
					list_result = new ArrayList<FriendSpaceResult>();
					for(int i=0; i<arr.length();i++){
						JSONObject obj_coach = arr.optJSONObject(i);
						FriendSpaceResult result = new FriendSpaceResult();
						result.subjectId = obj_coach.optString("subjectId");
						result.subjectName = obj_coach.optString("subjectName");
						result.videoPhoto = obj_coach.optString("videoPhoto");
						result.videoFileId = obj_coach.optString("videoFileId");
						result.videoUrl = obj_coach.optString("videoUrl");
						result.videoCode = obj_coach.optString("videoCode");
						result.sendTime = obj_coach.optString("sendTime");
						result.praiseNum = obj_coach.optString("praiseNum");
						result.commentNum = obj_coach.optString("commentNum");
						result.followAuthor = obj_coach.optString("followAuthor");
						result.coachComment = obj_coach.optString("coachComment");
						result.praiseAuthor = obj_coach.optString("praiseAuthor");
						
						list_result.add(result);
						result = null;
					}
				}
			}
		}
		return list_result;
	}

}
