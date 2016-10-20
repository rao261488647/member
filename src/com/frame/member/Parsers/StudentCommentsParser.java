package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.StudentCommentsResult;

public class StudentCommentsParser extends BaseParser<List<StudentCommentsResult>> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	-
//	"data": {
//	o-
//	"students": [
//	-
//	{
//	"friendId": "13",
//	"appHeadThumbnail": "",
//	"memberName": "",
//	   "memberLevel":"",
//	"commentContent": "好神奇的学员，超出我多年来的教学经验",
//	"commentTime": "2016-07-05"
//	"praiseAuthor": "0"
//	}
//	]
//	}
//	}
	@Override
	public List<StudentCommentsResult> parseJSON(String json) throws JSONException {
		List<StudentCommentsResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj = result_obj.optJSONObject("data");
			if(obj != null){
				JSONArray arr = obj.optJSONArray("students");
				if(arr != null && arr.length() > 0){
					list_result = new ArrayList<StudentCommentsResult>();
					for(int i=0; i<arr.length();i++){
						JSONObject obj_coach = arr.optJSONObject(i);
						StudentCommentsResult result = new StudentCommentsResult();
						result.friendId = obj_coach.optString("friendId");
						result.appHeadThumbnail = obj_coach.optString("appHeadThumbnail");
						result.memberName = obj_coach.optString("memberName");
						result.memberLevel = obj_coach.optString("memberLevel");
						result.commentContent = obj_coach.optString("commentContent");
						result.commentTime = obj_coach.optString("commentTime");
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
