package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.IntroduceAttentionResult;
import com.frame.member.bean.IntroduceAttentionResult.Coach;
import com.frame.member.bean.IntroduceAttentionResult.Friend;

public class IntroduceAttentionParser extends BaseParser<IntroduceAttentionResult> {

//	{
//	"code": "200",
//	"message": "返回数据成功",
//	-"data": {
//	-"coaches": [
//	
//	-{
//	"coachId": "3",
//	"headImg": "3_53381450257813.jpg",
//	"coachName": "邓国庆",
//	"titleName": "联盟教练"
//	}
//	],
//	-"friends": [
//	-{
//	"friendId": "1005",
//	"appHeadThumbnail": "",
//	"memberName": "",
//	"memberGrade": "0"
//	},
//	
//	]
//	}
//	}
	@Override
	public IntroduceAttentionResult parseJSON(String json) throws JSONException {
		IntroduceAttentionResult result = null;
		JSONObject obj_json = new JSONObject(json);
		if(obj_json != null){
			JSONObject obj_data = obj_json.optJSONObject("data");
			if(obj_data != null){
				result = new IntroduceAttentionResult();
				result.code = obj_json.optString("code");
				result.message = obj_json.optString("message");
				JSONArray arr_coach = obj_data.optJSONArray("coaches");
				JSONArray arr_friend = obj_data.optJSONArray("friends");
				if(arr_coach != null && arr_coach.length() > 0){
					List<Coach> list_coach = new ArrayList<Coach>();
					for(int i =0;i<arr_coach.length();i++){
						JSONObject obj_coach = arr_coach.optJSONObject(i);
						if(obj_coach != null){
							Coach result_coach = new Coach();
							result_coach.coachId = obj_coach.optString("coachId");
							result_coach.headImg = obj_coach.optString("headImg");
							result_coach.coachName = obj_coach.optString("coachName");
							result_coach.titleName = obj_coach.optString("titleName");
							list_coach.add(result_coach);
							result_coach = null;
						}
					}
					result.list_coach = list_coach;
				}
				if(arr_friend != null && arr_friend.length() > 0){
					List<Friend> list_friend = new ArrayList<Friend>();
					for(int i =0;i<arr_friend.length();i++){
						JSONObject obj_friend = arr_friend.optJSONObject(i);
						if(obj_friend != null){
							Friend result_friend = new Friend();
							result_friend.friendId = obj_friend.optString("friendId");
							result_friend.appHeadThumbnail = obj_friend.optString("appHeadThumbnail");
							result_friend.memberName = obj_friend.optString("memberName");
							result_friend.memberGrade = obj_friend.optString("memberGrade");
							list_friend.add(result_friend);
							result_friend = null;
						}
					}
					result.list_friend = list_friend;
				}
				
			}
		}
		return result;
		
	}

}
