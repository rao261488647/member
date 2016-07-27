package com.frame.member.bean;



public class RegisterLoginResult extends BaseBean {

	
	public String memberUserId,memberIden,memberlLevel,memberGrade,isTeacher,wxOpenId,wxHead,
			wxNiCheng,memberTel,memberName,memberSex,memberSign,memberBirth,memberProvince,
			memberCity,memberArea,memberAddress,regtime,memberPoints,memberMoney,
			isOpen,updeTime,recofollow,noticeset,memberFrom,token; //登录返回值
	
	@Override
	public String toString() {
		return "RegisterLoginResult [memberUserId=" + memberUserId
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
				+ memberFrom + ", token=" + token + "]";
	}

	
	
}