package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachDetailResult;
import com.frame.member.bean.CoachDetailResult.Photo;
import com.frame.member.bean.CoachSearchResult.Badges;

public class CoachDetailParser extends BaseParser<CoachDetailResult> {

	@Override
	public CoachDetailResult parseJSON(String json) throws JSONException {
		CoachDetailResult result = new CoachDetailResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			JSONObject obj_json = result_obj.optJSONObject("data");
			if(obj_json != null){
				JSONObject obj = obj_json.optJSONObject("coach");
				result.coachId = obj.optString("coachId");
				result.headImg = obj.optString("headImg");
				result.coachName = obj.optString("coachName");
				result.teacher = obj.optString("teacher");
				result.titlename = obj.optString("titlename");
				result.levelName = obj.optString("levelName");
				result.trainfee = obj.optString("trainfee");
				result.skifieldName = obj.optString("skifieldName");
				result.goal = obj.optInt("goal");
				result.intro = obj.optString("intro");
				result.areaName = obj.optString("areaName");
				result.skifieldName = obj.optString("skifieldName");
				result.honor = obj.optString("honor");
				result.specialty = obj.optString("specialty");
				result.isSigned = obj.optString("isSigned");
				result.collect = obj.optString("collect");
				result.meetNum = obj.optString("meetNum");
				JSONObject obj_video = obj.optJSONObject("video");
				if(obj_video != null){
					result.videoFileId = obj_video.optString("videoFileId");
					result.videoPhoto = obj_video.optString("videoPhoto");
					result.videoUrl = obj_video.optString("videoUrl");
				}
				
				JSONArray array_Json = obj.optJSONArray("photo");
				if(array_Json !=null && array_Json.length() > 0){
					Photo mPhoto;
					for(int i = 0;i< array_Json.length();i++){
						JSONObject obj_arr = array_Json.optJSONObject(i);
						mPhoto = new Photo();
						mPhoto.photoURL = obj_arr.optString("photoURL");
						result.photo.add(mPhoto);
						mPhoto = null;
					}
				}
				JSONArray arr_badges = obj.optJSONArray("badges");
				if(arr_badges !=null && arr_badges.length() > 0){
					Badges badge;
					for(int i = 0;i< arr_badges.length();i++){
						JSONObject obj_arr = array_Json.optJSONObject(i);
						if(obj_arr != null){
							badge = new Badges();
							badge.badgeId = obj_arr.optString("badgeId");
							badge.badgeName = obj_arr.optString("badgeName");
							result.badges.add(badge);
							badge = null;
						}
						
					}
				}
				
			}
		}
		return result;
	}

}
