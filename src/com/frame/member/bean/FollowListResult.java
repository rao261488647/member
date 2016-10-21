package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class FollowListResult extends BaseBean {
	
//	{
//		"code": 200,
//		"message": "返回数据成功",
//		"data": {
//		"coaches": {
//		"user": [
//		{
//		"coachId": "5",
//		"coachName": "王云龙",
//		"headImg": "http://wx.qlogo.cn/mmopen/4YO8iacYwY7qicIvicw0e0ILOhKMVmNg8tf5OwsZqflFIKqicbMAoKm1CMcwhsZXM0pOBiaAC6mXRB3oIfib5LGoXtGKYCAvhw9od2/0",
//		"titleName": "联盟特级",
//		"coachSign": "",
//		"followCoach": "0"
//		},
//	
//		]
//		},
//		"friends": {
//		"user": [
//		{
//		"friendId": "5",
//		"appHeadThumbnail": "",
//		"memberName": "毕晨浩",
//		"memberGrade": "0",
//		"memberSign": "",
//		"followFriend": "1"
//		},
//		
//		]
//		}
//		}
//		}

	public List<Coach> list_coach = new ArrayList<FollowListResult.Coach>();
	public List<Friends> list_friends = new ArrayList<Friends>();
	
	public static class Coach{
		public String coachId,coachName,headImg,titleName,coachSign,followCoach;
	}
	public static class Friends{
		public String friendId,appHeadThumbnail,memberName,memberGrade,memberSign,followFriend;
	}
	@Override
	public String toString() {
		return "FollowListResult [list_coach=" + list_coach + ", list_friends=" + list_friends + "]";
	}
	
	
}
