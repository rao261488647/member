package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.OrderSuccessResult;

public class OrderSuccessParser extends BaseParser<OrderSuccessResult> {
//	{
//	"code": "200",
//	"message": "课程预订成功",
//	-"data": {
//	-"order": {
//	"courseType": "1",
//	"coachId": "5",
//	"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//	"coachName": "王云龙",
//	"skifieldId": "3",
//	"skifieldName": "万龙滑雪场",
//	"dates": "2016.10.27 "
//	}
//	}
//	}
	@Override
	public OrderSuccessResult parseJSON(String json) throws JSONException {
		OrderSuccessResult result = new OrderSuccessResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
				if(obj_Json != null){
					JSONObject obj_order = obj_Json.optJSONObject("order");
					if(obj_order != null){
						result.courseType = obj_order.optString("courseType");
						if("1".equals(result.courseType)){
							result.coachId = obj_order.optString("coachId");
							result.headImg = obj_order.optString("headImg");
							result.coachName = obj_order.optString("coachName");
							result.skifieldId = obj_order.optString("skifieldId");
							result.skifieldName = obj_order.optString("skifieldName");
							result.dates = obj_order.optString("dates");
						}else{
							result.courseId = obj_order.optString("courseId");
							result.categoryName = obj_order.optString("categoryName");
							result.courseName = obj_order.optString("courseName");
							result.skifieldId = obj_order.optString("skifieldId");
							result.skifieldName = obj_order.optString("skifieldName");
							result.dates = obj_order.optString("dates");
						}
						
					}
				}
				
				
			}
			
		}
		return result;
	}

}
