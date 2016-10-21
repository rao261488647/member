package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.ClassDetailResult;

public class ClassDetailParser extends BaseParser<ClassDetailResult> {
//	{
//	"code": "200",
//	"message": "返回数据成功",
//	-"data": {
//	-"class": {
//	"courseId": "6",
//	"beginTime": "2015-11-23",
//	"overTime": "2015-11-27",
//	"hadDay": "4",
//	"planPrice": "50.00",
//	"courseName": "国职滑雪教练补考通道——理论知识",
//	"sdplate": "双板",
//	"courseIntro": "补考专用——理论知识",
//	"personNumber": "20",
//	"videoPhoto": "classvideo_17721471438200.jpg",
//	"videoFileid": "14651978969265585159",
//	"skifieldId": "1",
//	"skifieldAddr": "张家口市崇礼县西湾子镇小石夭村和平森林公园内",
//	"videoUrl": null,
//	"signedUpNum": "0",
//	"originalPrice": "50.00",
//	"discountPrice": "50",
//	"goal": 3,
//	"isSigned": "0",
//	"collect": "0"
//	}
//	}
//	}
	@Override
	public ClassDetailResult parseJSON(String json) throws JSONException {
		ClassDetailResult result = new ClassDetailResult();
		JSONObject result_obj = new JSONObject(json);
		if (result_obj != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			JSONObject obj = result_obj.optJSONObject("data");
			if(obj != null){
				JSONObject obj_result = obj.optJSONObject("class");
				if(obj_result != null){
					result.courseId = obj_result.optString("courseId");
					result.beginTime = obj_result.optString("beginTime");
					result.overTime = obj_result.optString("overTime");
					result.hadDay = obj_result.optString("hadDay");
					result.planPrice = obj_result.optString("planPrice");
					result.courseName = obj_result.optString("courseName");
					result.sdplate = obj_result.optString("sdplate");
					result.courseIntro = obj_result.optString("courseIntro");
					result.personNumber = obj_result.optString("personNumber");
					result.videoPhoto = obj_result.optString("videoPhoto");
					result.videoFileid = obj_result.optString("videoFileid");
					result.skifieldId = obj_result.optString("skifieldId");
					result.skifieldAddr = obj_result.optString("skifieldAddr");
					result.videoUrl = obj_result.optString("videoUrl");
					result.signedUpNum = obj_result.optString("signedUpNum");
					result.originalPrice = obj_result.optString("originalPrice");
					result.discountPrice = obj_result.optString("discountPrice");
					result.goal = obj_result.optInt("goal");
					result.isSigned = obj_result.optString("isSigned");
					result.collect = obj_result.optString("collect");
					
				}
			}
			
		}
		return result;
	}

}
