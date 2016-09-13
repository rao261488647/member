package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.AdviceFollowResult;
import com.frame.member.bean.AdviceFollowResult.Friends;
import com.frame.member.bean.AdviceFollowResult.User;

public class AdviceFollowParser extends BaseParser<List<AdviceFollowResult>> {

//	{
//	"code": 200,
//	"message": "返回数据成功",
//	"totalItems": 1,
//	-"data": {
//	-"follow": [
//	-{
//	"subjectId": "2",
//	"subjectName": "看我飞起来没有",
//	"videoPhoto": "http://www.baidu.com",
//	"videoFileId": "qcloud--VIDEOID",
//	"videoUrl": "http://www.gaga.com",
//	"sendTime": "2016-07-04 00:00:00",
//	"praiseNum": "7",
//	"commentNum": "0",
//	"praiseAuthor": "1",
//	-"user": {
//	"appHeadThumbnail": "",
//	"memberName": "姜明",
//	"memberGrade": "0",
//	"followAuthor": "1"
//	},
//	-"friends": [
//	-	{
//		"appHeadThumbnail": ""
//		},
//	],
//	"coachComment": "0",
//	"noReadNum": 3
//	}
//	]
//	}
//	}

	@Override
	public List<AdviceFollowResult> parseJSON(String json) throws JSONException {

		List<AdviceFollowResult> list_result = null; 
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			
			JSONObject obj_Json = obj.optJSONObject("data");
			if(obj_Json != null){
				
				JSONArray array = obj_Json.optJSONArray("follow");
				if(array != null && array.length() >0){
					list_result = new ArrayList<AdviceFollowResult>();
					AdviceFollowResult result;
					for(int i = 0 ; i< array.length(); i++){
						result = new AdviceFollowResult();
						JSONObject jsonObject = array.optJSONObject(i);
						result.code = obj.optString("code");
						result.message = obj.optString("message");
						result.totalItems = obj.optInt("totalItems");
						result.subjectId = jsonObject.optString("subjectId");
						result.subjectName = jsonObject.optString("subjectName");
						result.videoPhoto = jsonObject.optString("videoPhoto");
						result.videoFileId = jsonObject.optString("videoFileId");
						result.videoUrl = jsonObject.optString("videoUrl");
						result.coachComment = jsonObject.optString("coachComment");
						result.sendTime = jsonObject.optString("sendTime");
						result.praiseNum = jsonObject.optString("praiseNum");
						result.commentNum = jsonObject.optString("commentNum");
						result.praiseAuthor = jsonObject.optString("praiseAuthor");
						result.noReadNum = jsonObject.optInt("noReadNum");
						JSONObject obj_json = jsonObject.optJSONObject("user");
						if(obj_json != null){
							User user = new User();
							user.friendId = obj_json.optString("friendId");
							user.appHeadThumbnail = obj_json.optString("appHeadThumbnail");
							user.memberName = obj_json.optString("memberName");
							user.memberGrade = obj_json.optString("memberGrade");
							user.followAuthor = obj_json.optString("followAuthor");
							result.user = user;
							user = null;
						}
						JSONArray arr = jsonObject.optJSONArray("friends");
						if(arr != null && arr.length() > 0){
							List<Friends> list_friends = new ArrayList<Friends>();
							for(int j =0;j < arr.length();j++){
								Friends friend = new Friends();
								JSONObject obj_arr = arr.optJSONObject(j);
								friend.appHeadThumbnail = obj_arr.optString("appHeadThumbnail");
								list_friends.add(friend);
								friend = null;
							}
							result.list_friends = list_friends;
						}
						list_result.add(result);
						result = null;
						
					}
				}
			}
		}
		return list_result;
	}

}
