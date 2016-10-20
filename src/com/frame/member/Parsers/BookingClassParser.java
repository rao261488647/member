package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.BookingClassResult;

public class BookingClassParser extends BaseParser<List<BookingClassResult>> {

	@Override
	public List<BookingClassResult> parseJSON(String json) throws JSONException {
		List<BookingClassResult> list_result = null;
		if (json != null) {
			JSONObject result_obj = new JSONObject(json);
			if(result_obj != null){
				JSONObject obj = result_obj.optJSONObject("data");
				if(obj != null){
					JSONArray array = obj.optJSONArray("class");
					if(array != null && array.length() > 0 ){
						list_result = new ArrayList<BookingClassResult>();
						for(int i = 0;i<array.length();i++){
							JSONObject obj_json = array.optJSONObject(i);
							BookingClassResult result = new BookingClassResult();
							result.code = result_obj.optString("code");
							result.message = result_obj.optString("message");
							result.courseId = obj_json.optString("courseId");
							result.beginTime = obj_json.optString("beginTime");
							result.overTime = obj_json.optString("overTime");
							result.hadDay = obj_json.optString("hadDay");
							result.planPrice = obj_json.optString("planPrice");
							result.categoryName = obj_json.optString("categoryName");
							result.courseName = obj_json.optString("courseName");
							result.sdplate = obj_json.optString("sdplate");
							result.courseIntro = obj_json.optString("courseIntro");
							result.personNumber = obj_json.optString("personNumber");
							result.skifieldAddr = obj_json.optString("skifieldAddr");
							result.signedUpNum = obj_json.optString("signedUpNum");
							list_result.add(result);
							
						}
					}
				}
			}
		}
		return list_result;
	}

}
