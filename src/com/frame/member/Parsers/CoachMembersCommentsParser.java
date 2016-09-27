package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachMembersCommentsResult;
import com.frame.member.bean.CoachMembersCommentsResult.Tag;

public class CoachMembersCommentsParser extends BaseParser<List<CoachMembersCommentsResult>> {
//	{
//	    "code": "200", 
//	    "message": "返回数据成功", 
//	    "data": {
//	        "comment": [
//	            {
//	                "appHeadThumbnail": "", 
//	                "memberName": "", 
//	                "goal": "5", 
//	                "evaluateContent": "很喜欢教练的教学方式！", 
//	                "evaluateTime": "2016-08-31 19:21:45", 
//	                "tags": [
//	                    {
//	                        "name": "牛人"
//	                    }, 
//	                    {
//	                        "name": "高手"
//	                    }, 
//	                    {
//	                        "name": "好开心"
//	                    }
//	                ]
//	            }
//	        ]
//	    }
//	}
	@Override
	public List<CoachMembersCommentsResult> parseJSON(String json) throws JSONException {
		List<CoachMembersCommentsResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj = result_obj.optJSONObject("data");
			if(obj != null){
				JSONArray arr = obj.optJSONArray("comment");
				if(arr != null && arr.length() > 0){
					list_result = new ArrayList<CoachMembersCommentsResult>();
					for(int i=0; i<arr.length();i++){
						JSONObject obj_coach = arr.optJSONObject(i);
						CoachMembersCommentsResult result = new CoachMembersCommentsResult();
						result.friendId = obj_coach.optString("friendId");
						result.appHeadThumbnail = obj_coach.optString("appHeadThumbnail");
						result.memberName = obj_coach.optString("memberName");
						result.evaluateContent = obj_coach.optString("evaluateContent");
						result.evaluateTime = obj_coach.optString("evaluateTime");
						JSONArray arr_tags = obj_coach.optJSONArray("tags");
						if(arr_tags != null && arr_tags.length()>0){
							for(int j = 0;j<arr_tags.length();j++){
								JSONObject obj_tag = arr_tags.optJSONObject(j);
								if(obj_tag != null){
									Tag tag =  new Tag();
									tag.name = obj_tag.optString("name");
									result.tags.add(tag);
									tag = null;
								}
							}
							
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
