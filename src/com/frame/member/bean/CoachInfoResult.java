package com.frame.member.bean;


import com.frame.member.bean.BaseBean;



public class CoachInfoResult extends BaseBean {
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		"data": {
//		"coach": {
//		"coachId": "13",
//		"coachName": "张亚东",
//		"headImg": "13_78521447126621.jpg",
//		"coachIdentity": "1",
//		"istutor": "0",
//		"levelName": "五级指导员",
//		"titleName": "联盟高级",
//		"coachSign": ""
//		},
//		"followCoach": "0"
//		}
//		}
	public String coachId,headImg,coachName,coachIdentity,istutor,
				levelName,titleName,coachSign,followCoach;

	@Override
	public String toString() {
		return "CoachInfoResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", coachIdentity=" + coachIdentity + ", istutor=" + istutor + ", levelName=" + levelName
				+ ", titleName=" + titleName + ", coachSign=" + coachSign + ", followCoach=" + followCoach + "]";
	}

}