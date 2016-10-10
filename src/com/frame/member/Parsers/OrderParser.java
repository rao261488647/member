package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.OrderResult;

public class OrderParser extends BaseParser<OrderResult> {
//	{
//		"code": "200",
//		"message": "返回信息成功",
//		-"data": {
//		"appid": "wxaff36b3a7ba66e5f",
//		"partnerid": "1379153302",
//		"package": "Sign=WXPay",
//		"noncestr": "YSKtNjghZhEoZ4FI",
//		"timestamp": "1475978543",
//		"prepayid": "wx20161009100224010d43f8bd0773680388",
//		"sign": "8F8FB6CFD8B166A85E5A1AABB1492510",
//		"payorderNum": "549320161009100221",
//		"price": 1,
//		"url": "http://api.flowerski.com/pay/wxpay/otocoachnotify.php"
//		}
//		}
	@Override
	public OrderResult parseJSON(String json) throws JSONException {
		OrderResult result = new OrderResult();
		JSONObject result_obj = new JSONObject(json);
		if (json != null) {
			result.code = result_obj.optString("code");
			result.message = result_obj.optString("message");
			if("200".equals(result.code)){
				JSONObject obj_Json = result_obj.optJSONObject("data");
				result.appid = obj_Json.optString("appid");
				result.partnerid = obj_Json.optString("partnerid");
				result.packages = obj_Json.optString("package");
				result.noncestr = obj_Json.optString("noncestr");
				result.timestamp = obj_Json.optString("timestamp");
				result.prepayid = obj_Json.optString("prepayid");
				result.sign = obj_Json.optString("sign");
				result.payorderNum = obj_Json.optString("payorderNum");
				result.price = obj_Json.optString("price");
				result.url = obj_Json.optString("url");
				
			}
			
		}
		return result;
	}

}
