package com.frame.member.bean;



public class TokenResult extends BaseBean {
//	{
//	    "code": "200",
//	    "message": "返回数据成功",
//	    "data": {
//	        "token": "1ds2ae33g332jwew88"
//	    }
//	}
	public String token;

	@Override
	public String toString() {
		return "TokenResult [token=" + token + "]";
	}
	
	
}