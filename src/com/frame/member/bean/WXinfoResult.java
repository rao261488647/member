package com.frame.member.bean;

public class WXinfoResult extends BaseBean {
//	{ 
//		"openid":"OPENID",
//		"nickname":"NICKNAME",
//		"sex":1,
//		"province":"PROVINCE",
//		"city":"CITY",
//		"country":"COUNTRY",
//		"headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//		"privilege":[
//		"PRIVILEGE1", 
//		"PRIVILEGE2"
//		],
//		"unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//
//		}

	public String openid;
	public String nickname;
	public String sex;
	public String province;
	public String city;
	public String country;
	public String unionid;
	public String headimgurl;
	
	@Override
	public String toString() {
		return "WXinfoResult [openid=" + openid + ", nickname=" + nickname
				+ ", sex=" + sex + ", province=" + province + ", city=" + city
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", unionid=" + unionid + "]";
	}


}
