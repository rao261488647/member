package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachSearchResult;

public class CoachSearchParser extends BaseParser<List<CoachSearchResult>> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	"totalItems": 1,
//	-"data": {
//	-"coaches": [
//	-{
//	"coachId": "24",
//	"coachName": "孙小元",
//	"headImg": "http://wx.qlogo.cn/mmopen/PVJAFu3VTicYicAOt6qThBZw3TE6mt6DZ4YP0klibQdOPmYcyl0RiaN4NdrabsSaKa268AL60ick3Liar2Kl1OcNouNCoxdUJXEjzc/0",
//	"coachTitle": "5",
//	"coachBadge": "0"
//	}
//	]
//	}
//	}
	@Override
	public List<CoachSearchResult> parseJSON(String json) throws JSONException {
		List<CoachSearchResult> list_result = null;
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			JSONObject obj_data = result_obj.optJSONObject("data");
			if(obj_data != null){
				JSONArray arr_coaches = obj_data.optJSONArray("coaches");
				if(arr_coaches != null && arr_coaches.length()> 0){
					list_result = new ArrayList<CoachSearchResult>();
					for(int i = 0;i<arr_coaches.length();i++){
						JSONObject obj_coach = arr_coaches.optJSONObject(i);
						if(obj_coach != null){
							CoachSearchResult result = new CoachSearchResult();
							result.code = result_obj.optString("code");
							result.message = result_obj.optString("message");
							result.totalItems = result_obj.optInt("totalItems");
							result.coachId = obj_coach.optString("coachId");
							result.coachName = obj_coach.optString("coachName");
							result.headImg = obj_coach.optString("headImg");
							result.coachTitle = obj_coach.optString("coachTitle");
							result.coachBadge = obj_coach.optString("coachBadge");
							list_result.add(result);
							result = null;
						}
					}
				}
			}
		}
		return list_result;
	}

}
