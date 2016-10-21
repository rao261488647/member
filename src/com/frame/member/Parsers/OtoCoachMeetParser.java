package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.OtoCoachMeetResult;
import com.frame.member.bean.OtoCoachMeetResult.Date;
import com.frame.member.bean.OtoCoachMeetResult.SkifieldChoices;

public class OtoCoachMeetParser extends BaseParser<OtoCoachMeetResult> {

	@Override
	public OtoCoachMeetResult parseJSON(String json) throws JSONException {
		OtoCoachMeetResult result = new OtoCoachMeetResult();
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
				result.trainfee = obj.optString("trainfee");
				result.coachBadge = obj.optString("coachBadge");
				result.areaId = obj.optString("areaId");
				result.titleName = obj.optString("titleName");
				result.skifieldName = obj.optString("skifieldName");
				result.coachStar = obj.optString("coachStar");
				
				JSONObject json_area = obj_json.optJSONObject("snowArea");
				if(json_area != null){
					result.areaName = json_area.optString("areaName");
				}
				JSONArray array_field = obj_json.optJSONArray("skifieldChoices");
				if(array_field!= null && array_field.length()>0){
					for(int j = 0;j< array_field.length();j++){
						JSONObject obj_field = array_field.optJSONObject(j);
						if(obj_field!= null){
							SkifieldChoices field = new SkifieldChoices();
							field.skifieldId = obj_field.optString("skifieldId");
							field.skifieldName = obj_field.optString("skifieldName");
							result.skifieldChoices.add(field);
							field = null;
						}
					}
				}
				JSONArray array_Json = obj_json.optJSONArray("date");
				if(array_Json !=null && array_Json.length() > 0){
					Date mDate;
					for(int i = 0;i< array_Json.length();i++){
						JSONObject obj_arr = array_Json.optJSONObject(i);
						mDate = new Date();
						mDate.value = obj_arr.optString("value");
						mDate.status = obj_arr.optString("status");
						result.list_date.add(mDate);
						mDate = null;
					}
				}
				
			}
		}
		return result;
	}

}
