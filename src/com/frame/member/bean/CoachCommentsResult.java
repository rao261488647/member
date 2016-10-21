package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class CoachCommentsResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-
//		"data": {
//		"coaches": [
//		-
//		{
//		"coachId": "5",
//		"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//		"coachName": "王云龙",
//		"levelName": "五级指导员",
//		"commentContent": "好神奇的学员，超出我多年来的教学经验",
//		"commentTime": "2016-07-05 10:52:23",
//		"praiseNum": "19",
//		"followCoach": "0"
//		"praiseCoach": "0"
//		}
//		]
//		}
//		}

	public String coachId,headImg,coachName,levelName,commentContent,commentTime,praiseNum,
					followCoach,praiseCoach;

	@Override
	public String toString() {
		return "CoachCommentsResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", levelName=" + levelName + ", commentContent=" + commentContent + ", commentTime=" + commentTime
				+ ", praiseNum=" + praiseNum + ", followCoach=" + followCoach + ", praiseCoach=" + praiseCoach + "]";
	}
	
	
}
