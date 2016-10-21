package com.frame.member.bean;



public class OrderSuccessResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "课程预订成功",
//		-"data": {
//		-"order": {
//		"courseType": "1",
//		"coachId": "5",
//		"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//		"coachName": "王云龙",
//		"skifieldId": "3",
//		"skifieldName": "万龙滑雪场",
//		"dates": "2016.10.27 "
//		}
//		}
//		}
//	{
//		"code": "200",
//		"message": "课程预订成功",
//		-
//		"data": {
//		o-
//		"order": {
//		"courseType": "2",
//		"courseId": "6",
//		"courseName": "国职滑雪教练培训班—四级",
//		"categoryName": "指导员培训",
//		"skifieldId": "1",
//		"skifieldName": "长城岭滑雪场",
//		"dates": "2015.11.23~2015.11.27 共4天"
//		}
//		}
//		}
	public String courseType,coachId,headImg,coachName,skifieldId,skifieldName,dates,
					courseId,courseName,categoryName;

	@Override
	public String toString() {
		return "OrderSuccessResult [courseType=" + courseType + ", coachId=" + coachId + ", headImg=" + headImg
				+ ", coachName=" + coachName + ", skifieldId=" + skifieldId + ", skifieldName=" + skifieldName
				+ ", dates=" + dates + ", courseId=" + courseId + ", courseName=" + courseName + ", categoryName="
				+ categoryName + "]";
	}

	

}
