package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.AdviceDetailResult;
import com.frame.member.bean.AdviceDetailResult.Friends;
import com.frame.member.bean.AdviceDetailResult.User;

public class AdviceDetailParser extends BaseParser<AdviceDetailResult> {
//	{
//    "code": "200", 
//    "message": "返回数据成功", 
//    "data": {
//        "friendId": "10", 
//        "subjectId": "2", 
//        "subjectName": "看我飞起来没有", 
//        "videoPhoto": "http://www.baidu.com", 
//        "videoFileId": "qcloud--VIDEOID", 
//        "videoUrl": "http://www.gaga.com", 
//        "videoCode": "playcode", 
//        "sendTime": "2016-07-04 00:00:00", 
//        "praiseNum": "7", 
//        "commentNum": "0", 
//        "praiseAuthor": "0", 
//        "user": {
//            "friendId": "10", 
//            "appHeadThumbnail": "", 
//            "memberName": "姜明", 
//            "memberGrade": "0"
//        }, 
//        "followAuthor": "0", 
//        "friends": [
//            {
//                "appHeadThumbnail": ""
//            }, 
//            {
//                "appHeadThumbnail": ""
//            }
//        ]
//    }
//}
	@Override
	public AdviceDetailResult parseJSON(String json) throws JSONException {
		AdviceDetailResult result = new AdviceDetailResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			JSONObject obj = result_obj.optJSONObject("data");
			result.friendId = obj.optString("friendId");
			result.subjectId = obj.optString("subjectId");
			result.subjectName = obj.optString("subjectName");
			result.videoPhoto = obj.optString("videoPhoto");
			result.videoFileId = obj.optString("videoFileId");
			result.videoUrl = obj.optString("videoUrl");
			result.videoCode = obj.optString("videoCode");
			result.sendTime = obj.optString("sendTime");
			result.praiseNum = obj.optString("praiseNum");
			result.commentNum = obj.optString("commentNum");
			result.praiseAuthor = obj.optString("praiseAuthor");
			result.followAuthor = obj.optString("followAuthor");
			JSONObject obj_user = obj.optJSONObject("user");
			if(obj_user != null){
				User user = new User();
				user.friendId = obj_user.optString("friendId");
				user.appHeadThumbnail = obj_user.optString("appHeadThumbnail");
				user.memberName = obj_user.optString("memberName");
				user.memberGrade = obj_user.optString("memberGrade");
				result.user = user;
			}
			JSONArray arr = obj.optJSONArray("friends");
			if(arr != null && arr.length() > 0){
				List<Friends> list_friends = new ArrayList<Friends>();
				for(int i = 0; i<arr.length(); i++){
					JSONObject obj_friends = arr.optJSONObject(i);
					if(obj_friends != null){
						Friends friends = new Friends();
						friends.appHeadThumbnail = obj_friends.optString("appHeadThumbnail");
						list_friends.add(friends);
						friends = null;
					}
					
				}
				result.list_friends = list_friends;
			}
			
		}
		return result;
	}

}
