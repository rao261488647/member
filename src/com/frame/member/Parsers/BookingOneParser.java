package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.BookingOneResult;
import com.frame.member.bean.BookingOneResult.Badges;
import com.frame.member.bean.BookingOneResult.Coach;

public class BookingOneParser extends BaseParser<BookingOneResult> {

	
	@Override
	public BookingOneResult parseJSON(String json) throws JSONException {

		BookingOneResult result = null;
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			result = new BookingOneResult();
			result.code = obj.optString("code");
			result.message = obj.optString("message");
			result.totalItems = obj.optString("totalItems");
			JSONObject obj_Json = obj.optJSONObject("data");
			if(obj_Json != null){
				
				JSONArray array_5 = obj_Json.optJSONArray("coaches");
				if(array_5 != null && array_5.length() >0){
					for(int i = 0 ; i< array_5.length(); i++){
						JSONObject jsonObject = array_5.optJSONObject(i);
						Coach coach = new Coach();
						coach.coachId = jsonObject.optString("coachId");
						coach.coachName = jsonObject.optString("coachName");
						coach.headImg = jsonObject.optString("headImg");
						coach.coachBadge = jsonObject.optString("coachBadge");
						coach.levelName = jsonObject.optString("levelName");
						coach.meetNum = jsonObject.optString("meetNum");
						coach.goal = (float) jsonObject.optDouble("goal");
						JSONArray arr_badges = jsonObject.optJSONArray("badges");
						if(arr_badges != null && arr_badges.length() > 0){
							for(int j = 0;j<arr_badges.length();j++){
								JSONObject obj_badges = arr_badges.optJSONObject(j);
								if(obj_badges != null){
									Badges badge = new Badges();
									badge.badgeId = obj_badges.optString("badgeId");
									badge.badgeName = obj_badges.optString("badgeName");
									coach.badges.add(badge);
									badge = null;
								}
							}
							
						}
						result.coaches.add(coach);
					}
				}
			}
		}
		return result;
	}

}
