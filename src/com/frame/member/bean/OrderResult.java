package com.frame.member.bean;



public class OrderResult extends BaseBean {
	
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
	public String appid,partnerid,packages,noncestr,timestamp,prepayid,sign,
			payorderNum,price,url;

	@Override
	public String toString() {
		return "OrderResult [appid=" + appid + ", partnerid=" + partnerid + ", packages=" + packages + ", noncestr="
				+ noncestr + ", timestamp=" + timestamp + ", prepayid=" + prepayid + ", sign=" + sign + ", payorderNum="
				+ payorderNum + ", price=" + price + ", url=" + url + "]";
	}
	
	
	
}
