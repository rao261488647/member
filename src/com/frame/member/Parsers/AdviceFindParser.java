package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.AdviceFindResult;
import com.frame.member.bean.AdviceFindResult.Friends;
import com.frame.member.bean.AdviceFindResult.User;

public class AdviceFindParser extends BaseParser<List<AdviceFindResult>> {

	
	@Override
	public List<AdviceFindResult> parseJSON(String json) throws JSONException {

		List<AdviceFindResult> list_result = null; 
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			
			JSONObject obj_Json = obj.optJSONObject("data");
			if(obj_Json != null){
				
				JSONArray array = obj_Json.optJSONArray("found");
				if(array != null && array.length() >0){
					list_result = new ArrayList<AdviceFindResult>();
					AdviceFindResult result;
					for(int i = 0 ; i< array.length(); i++){
						result = new AdviceFindResult();
						JSONObject jsonObject = array.optJSONObject(i);
						result.code = obj.optString("code");
						result.message = obj.optString("message");
						result.totalItems = obj.optInt("totalItems");
						result.subjectId = jsonObject.optString("subjectId");
						result.subjectName = jsonObject.optString("subjectName");
						result.videoPhoto = jsonObject.optString("videoPhoto");
						result.videoFileId = jsonObject.optString("videoFileId");
						result.videoUrl = jsonObject.optString("videoUrl");
						result.videoCode = jsonObject.optString("videoCode");
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
							List<Friends> list_friends = new ArrayList<AdviceFindResult.Friends>();
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
