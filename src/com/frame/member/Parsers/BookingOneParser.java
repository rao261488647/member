package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.BookingOneResult;
import com.frame.member.bean.BookingOneResult.Coach;
import com.frame.member.bean.BookingOneResult.LevelChoice;
import com.frame.member.bean.BookingOneResult.SkifieldChoice;

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
				JSONArray array_1 = obj_Json.optJSONArray("sexChoices");
				if(array_1 !=null && array_1.length() > 0){
					for(int i = 0;i< array_1.length();i++ ){
						result.sexChoices.add(array_1.optString(i));
					}
				}
				JSONArray array_2 = obj_Json.optJSONArray("sdplateChoices");
				if(array_2 != null && array_2.length() > 0){
					for(int i = 0; i< array_2.length(); i++){
						result.sdplateChoices.add(array_2.optString(i));
					}
				}
				JSONArray array_3 = obj_Json.optJSONArray("skifieldChoices");
				if(array_3 != null && array_3.length() >0){
					for(int i = 0 ; i< array_3.length(); i++){
						JSONObject jsonObject = array_3.optJSONObject(i);
						SkifieldChoice sc = new SkifieldChoice();
						sc.skifieldId = jsonObject.optString("skifieldId");
						sc.skifieldName = jsonObject.optString("skifieldName");
						result.skifieldChoices.add(sc);
					}
				}
				JSONArray array_4 = obj_Json.optJSONArray("levelChoices");
				if(array_4 != null && array_4.length() >0){
					for(int i = 0 ; i< array_4.length(); i++){
						JSONObject jsonObject = array_4.optJSONObject(i);
						LevelChoice lc = new LevelChoice();
						lc.levelId = jsonObject.optString("levelId");
						lc.levelName = jsonObject.optString("levelName");
						result.levelChoices.add(lc);
					}
				}
				JSONArray array_5 = obj_Json.optJSONArray("coaches");
				if(array_5 != null && array_5.length() >0){
					for(int i = 0 ; i< array_5.length(); i++){
						JSONObject jsonObject = array_5.optJSONObject(i);
						Coach coach = new Coach();
						coach.coachId = jsonObject.optString("coachId");
						coach.coachName = jsonObject.optString("coachName");
						coach.headImg = jsonObject.optString("headImg");
						coach.badgeName = jsonObject.optString("badgeName");
						coach.levelName = jsonObject.optString("levelName");
						result.coaches.add(coach);
					}
				}
			}
		}
		return result;
	}

}
