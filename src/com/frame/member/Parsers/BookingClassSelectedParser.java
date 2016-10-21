package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.BookingClassSelectedResult;
import com.frame.member.bean.BookingClassSelectedResult.AreaChoices;
import com.frame.member.bean.BookingClassSelectedResult.CategoryChoices;
import com.frame.member.bean.BookingClassSelectedResult.SkifieldChoices;

public class BookingClassSelectedParser extends BaseParser<BookingClassSelectedResult> {

	
	@Override
	public BookingClassSelectedResult parseJSON(String json) throws JSONException {

		BookingClassSelectedResult result = null;
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			result = new BookingClassSelectedResult();
			result.code = obj.optString("code");
			result.message = obj.optString("message");
			JSONObject obj_Json = obj.optJSONObject("data");
			if(obj_Json != null){
				JSONArray array_1 = obj_Json.optJSONArray("areaChoices");
				if(array_1 !=null && array_1.length() > 0){
					for(int i = 0;i< array_1.length();i++ ){
						JSONObject obj_are = array_1.optJSONObject(i);
						AreaChoices ac = new AreaChoices();
						ac.areaId = obj_are.optString("areaId");
						ac.areaName = obj_are.optString("areaName");
						result.areaChoices.add(ac);
						ac = null;
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
						SkifieldChoices sc = new SkifieldChoices();
						sc.skifieldId = jsonObject.optString("skifieldId");
						sc.skifieldName = jsonObject.optString("skifieldName");
						result.skifieldChoices.add(sc);
						sc = null;
					}
				}
				JSONArray array_4 = obj_Json.optJSONArray("categoryChoices");
				if(array_4 != null && array_4.length() >0){
					for(int i = 0 ; i< array_4.length(); i++){
						JSONObject jsonObject = array_4.optJSONObject(i);
						CategoryChoices lc = new CategoryChoices();
						lc.categoryId = jsonObject.optString("categoryId");
						lc.categoryName = jsonObject.optString("categoryName");
						result.categoryChoices.add(lc);
					}
				}
				
			}
		}
		return result;
	}

}
