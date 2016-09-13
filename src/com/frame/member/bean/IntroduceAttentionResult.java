package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class IntroduceAttentionResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-"data": {
//		-"coaches": [
//		
//		-{
//		"coachId": "3",
//		"headImg": "3_53381450257813.jpg",
//		"coachName": "邓国庆",
//		"titleName": "联盟教练"
//		}
//		],
//		-"friends": [
//		-{
//		"friendId": "1005",
//		"appHeadThumbnail": "",
//		"memberName": "",
//		"memberGrade": "0"
//		},
//		
//		]
//		}
//		}
	public List<Coach> list_coach = new ArrayList<IntroduceAttentionResult.Coach>();
	public List<Friend> list_friend = new ArrayList<IntroduceAttentionResult.Friend>();

	public static class Coach{
		public String coachId,headImg,coachName,titleName;
	}
	public static class Friend{
		public String friendId,appHeadThumbnail,memberName,memberGrade;
	}
	
	@Override
	public String toString() {
		return "IntroduceAttentionResult [list_coach=" + list_coach + ", list_friend=" + list_friend + "]";
	}
	
	
}
