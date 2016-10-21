package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.RegisterLoginResult;
import com.frame.member.bean.TokenResult;


//public String memberUserId,memberIden,memberlLevel,memberGrade,isTeacher,wxOpenId,wxHead,
//wxNiCheng,memberTel,memberName,memberSex,memberSign,memberBirth,memberProvince,
//memberCity,memberArea,memberAddress,regtime,memberPoints,memberMoney,
//isOpen,updeTime,recofollow,noticeset,memberFrom; //登录返回值

public class RegisterLoginParser extends BaseParser<RegisterLoginResult> {

	@Override
	public RegisterLoginResult parseJSON(String json) throws JSONException {
		RegisterLoginResult result = new RegisterLoginResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
					result.token = obj_Json.optString("token");
					result.memberUserId = obj_Json.optString("memberUserId");
					result.memberIden = obj_Json.optString("memberIden");
					result.memberlLevel = obj_Json.optString("memberlLevel");
					result.memberGrade = obj_Json.optString("memberGrade");
					result.isTeacher = obj_Json.optString("isTeacher");
					result.wxOpenId = obj_Json.optString("wxOpenId");
					result.wxHead = obj_Json.optString("wxHead");
					result.wxNiCheng = obj_Json.optString("wxNiCheng");
					result.memberTel = obj_Json.optString("memberTel");
					result.memberName = obj_Json.optString("memberName");
					result.memberSex = obj_Json.optString("memberSex");
					result.memberSign = obj_Json.optString("memberSign");
					result.memberBirth = obj_Json.optString("memberBirth");
					result.memberProvince = obj_Json.optString("memberProvince");
					result.memberCity = obj_Json.optString("memberCity");
					result.memberArea = obj_Json.optString("memberArea");
					result.memberAddress = obj_Json.optString("memberAddress");
					result.regtime = obj_Json.optString("regtime");
					result.memberPoints = obj_Json.optString("memberPoints");
					result.memberMoney = obj_Json.optString("memberMoney");
					result.isOpen = obj_Json.optString("isOpen");
					result.updeTime = obj_Json.optString("updeTime");
					result.recofollow = obj_Json.optString("recofollow");
					result.noticeset = obj_Json.optString("noticeset");
					result.memberFrom = obj_Json.optString("memberFrom");
					result.token = obj_Json.optString("token");
				}
			}
			
		return result;
	}

}
