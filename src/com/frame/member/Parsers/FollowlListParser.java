package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.FollowListResult;
import com.frame.member.bean.FollowListResult.Coach;
import com.frame.member.bean.FollowListResult.Friends;

public class FollowlListParser extends BaseParser<FollowListResult> {

//	{
//	"code": 200,
//	"message": "返回数据成功",
//	"data": {
//	"coaches": {
//	"user": [
//	{
//	"coachId": "5",
//	"coachName": "王云龙",
//	"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//	"levelName": "联盟特级",
//	"coachSign": "",
//	"followCoach": "0"
//	},
//
//	]
//	},
//	"friends": {
//	"user": [
//	{
//	"friendId": "5",
//	"appHeadThumbnail": "",
//	"memberName": "毕晨浩",
//	"memberGrade": "0",
//	"memberSign": "",
//	"followFriend": "1"
//	},
//	
//	]
//	}
//	}
//	}
	@Override
	public FollowListResult parseJSON(String json) throws JSONException {

		FollowListResult result = new FollowListResult(); 
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			result.code = obj.optString("code");
			result.message = obj.optString("message");
			JSONObject obj_data = obj.optJSONObject("data");
			if(obj_data != null){
				JSONObject obj_coaches = obj_data.optJSONObject("coaches");
				if(obj_coaches != null){
					JSONArray arr_coach = obj_coaches.optJSONArray("user");
					if(arr_coach != null && arr_coach.length() > 0){
						List<Coach> list_coach = new ArrayList<FollowListResult.Coach>();
						for(int i = 0;i<arr_coach.length();i++){
							JSONObject obj_user = arr_coach.optJSONObject(i); 
							if(obj_user != null){
								Coach coach = new Coach();
								coach.coachId = obj_user.optString("coachId");
								coach.coachName = obj_user.optString("coachName");
								coach.headImg = obj_user.optString("headImg");
								coach.titleName = obj_user.optString("titleName");
								coach.coachSign = obj_user.optString("coachSign");
								coach.followCoach = obj_user.optString("followCoach");
								list_coach.add(coach);
								coach = null;
							}
						}
						result.list_coach = list_coach;
						
					}
				}
				JSONObject obj_friends = obj_data.optJSONObject("friends");
				if(obj_friends != null){
					JSONArray arr_friends = obj_friends.optJSONArray("user");
					if(arr_friends != null && arr_friends.length() > 0){
						List<Friends> list_friends = new ArrayList<FollowListResult.Friends>();
						for(int i = 0;i<arr_friends.length();i++){
							JSONObject obj_user = arr_friends.optJSONObject(i); 
							if(obj_user != null){
								Friends friends = new Friends();
								friends.friendId = obj_user.optString("friendId");
								friends.appHeadThumbnail = obj_user.optString("appHeadThumbnail");
								friends.memberName = obj_user.optString("memberName");
								friends.memberGrade = obj_user.optString("memberGrade");
								friends.memberSign = obj_user.optString("memberSign");
								friends.followFriend = obj_user.optString("followFriend");
								list_friends.add(friends);
								friends = null;
							}
						}
						result.list_friends = list_friends;
					}
				}
			}
		}
		return result;
	}

}
