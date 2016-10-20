package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.MemberInfoResult;

public class MemberInfoParser extends BaseParser<MemberInfoResult> {
//	{
//	•	"code": "200",
//	•	"message": "返回数据成功",
//	•	-
//	"data": {
//	o	"level": "0",
//	o	-
//	"card": {
//		-
//	"green": {
//		"感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。\r\n\t\t\t当您注册成为平台会员，即可成为绿卡会员，将享有以下权益：\r\n\t\t\t1.将有权使用平台的会员功能服务；\r\n\t\t\t2.将即时建立个人永久滑雪学习档案。\r\n\t\t\t感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。"
//	},
//		-
//	"blue": {
//		"感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。\r\n\t\t\t当您注册成为平台会员，即可成为绿卡会员，将享有以下权益：\r\n\t\t\t1.将有权使用平台的会员功能服务；\r\n\t\t\t2.将即时建立个人永久滑雪学习档案。\r\n\t\t\t感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。"
//	},
//		-
//	"black": {
//		"感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。\r\n\t\t\t当您注册成为平台会员，即可成为绿卡会员，将享有以下权益：\r\n\t\t\t1.将有权使用平台的会员功能服务；\r\n\t\t\t2.将即时建立个人永久滑雪学习档案。\r\n\t\t\t感谢您注册成为“郝世花滑雪”平台会员。平台汇聚全球雪场资源，集成优秀教练团队，旨在为滑雪爱好者提供专业、安全、贴心的滑雪教学服务。平台拥有丰富的滑雪培训课程，并能全程记录您的滑雪成长轨迹。系统预定操作简单快捷，支付方式便捷安全，是您闯荡滑雪世界的忠实伴侣和助手。"
//	}
//	},
//	o	-
//	"upgrade": {
//		"flowerRank": "300.00",//绿卡升级为蓝卡所需金额
//		"vipRank": "1314.00",//绿卡升级为黑卡所需金额
//		"blueRank": "1188.00"//蓝卡升级为黑卡所需金额
//	}
//	}
	@Override
	public MemberInfoResult parseJSON(String json) throws JSONException {
		MemberInfoResult result = new MemberInfoResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
				result.level = obj_Json.optString("level");
				JSONObject obj_card = obj_Json.optJSONObject("card");
				if(obj_card != null){
					result.green = obj_card.optString("green");
					result.blue = obj_card.optString("blue");
					result.black = obj_card.optString("black");
				}
				JSONObject obj_upgrade = obj_Json.optJSONObject("upgrade");
				if(obj_upgrade != null){
					result.flowerRank = obj_upgrade.optString("flowerRank");
					result.vipRank = obj_upgrade.optString("vipRank");
					result.blueRank = obj_upgrade.optString("blueRank");
				}
			}
			
		}
		return result;
	}

}
