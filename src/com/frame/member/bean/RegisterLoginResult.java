package com.frame.member.bean;



public class RegisterLoginResult extends BaseBean {
//	{
//	    "code": "200",
//	    "message": "注册成功",
//	    "data": {
//	        "mobile": "xxxxxxx",
//	        "token": "12esf32214jgy6df31",
//	        "tokenTime": "14824769347",
//	    }
//	}
	public String mobile,token,tokenTime;//注册返回值
	public String memberUserId,memberIden,memberlLevel,memberGrade,isTeacher,wxOpenId,wxHead,
			wxNiCheng,memberTel,memberName,memberSex,memberSign,memberBirth,memberProvince,
			memberCity,memberArea,memberAddress,regtime,memberPoints,memberMoney,
			isOpen,updeTime,recofollow,noticeset,memberFrom; //登录返回值
	@Override
	public String toString() {
		return "RegiserLoginResult [mobile=" + mobile + ", token=" + token
				+ ", tokenTime=" + tokenTime + ", memberUserId=" + memberUserId
				+ ", memberIden=" + memberIden + ", memberlLevel="
				+ memberlLevel + ", memberGrade=" + memberGrade
				+ ", isTeacher=" + isTeacher + ", wxOpenId=" + wxOpenId
				+ ", wxHead=" + wxHead + ", wxNiCheng=" + wxNiCheng
				+ ", memberTel=" + memberTel + ", memberName=" + memberName
				+ ", memberSex=" + memberSex + ", memberSign=" + memberSign
				+ ", memberBirth=" + memberBirth + ", memberProvince="
				+ memberProvince + ", memberCity=" + memberCity
				+ ", memberArea=" + memberArea + ", memberAddress="
				+ memberAddress + ", regtime=" + regtime + ", memberPoints="
				+ memberPoints + ", memberMoney=" + memberMoney + ", isOpen="
				+ isOpen + ", updeTime=" + updeTime + ", recofollow="
				+ recofollow + ", noticeset=" + noticeset + ", memberFrom="
				+ memberFrom + "]";
	}

	
	
}