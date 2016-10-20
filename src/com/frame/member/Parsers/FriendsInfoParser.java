package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.FriendsInfoResult;

public class FriendsInfoParser extends BaseParser<FriendsInfoResult> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	"data": {
//	"user": {
//	"friendId": "10",
//	"appHeadThumbnail": "",
//	"memberName": "姜明",
//	"memberGrade": "0",
//	"memberlLevel": "1",
//	"memberSign": "",
//	"followFriend": "0"
//	}
//	}
//	}
	@Override
	public FriendsInfoResult parseJSON(String json) throws JSONException {
		FriendsInfoResult result = new FriendsInfoResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			JSONObject obj_json = result_obj.optJSONObject("data");
			if(obj_json != null){
				JSONObject json_obj = obj_json.optJSONObject("user");
				if(json_obj != null){
					result.friendId = json_obj.optString("friendId");
					result.appHeadThumbnail = json_obj.optString("appHeadThumbnail");
					result.memberName = json_obj.optString("memberName");
					result.memberGrade = json_obj.optString("memberGrade");
					result.memberlLevel = json_obj.optString("memberlLevel");
					result.memberSign = json_obj.optString("memberSign");
					result.followFriend = json_obj.optString("followFriend");
				}
				
			}
		}
		return result;
	}

}
