package com.frame.member.bean;

import com.frame.member.bean.BaseBean;



public class LoginCodeResult extends BaseBean {
//	{
//	    "code": "200",
//	    "message": "返回数据成功",
//	    "data": {
//	        "verificationCode": "1058"
//	    }
//	}
	public String verificationCode;

	@Override
	public String toString() {
		return "LoginCodeResult [verificationCode=" + verificationCode + "]";
	}

	
	
	
}