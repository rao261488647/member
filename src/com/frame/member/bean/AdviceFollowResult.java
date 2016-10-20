package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class AdviceFollowResult extends BaseBean {
	
//	{
//		"code": 200,
//		"message": "返回数据成功",
//		"totalItems": 1,
//		-"data": {
//		-"follow": [
//		-{
//		"subjectId": "2",
//		"subjectName": "看我飞起来没有",
//		"videoPhoto": "http://www.baidu.com",
//		"videoFileId": "qcloud--VIDEOID",
//		"videoUrl": "http://www.gaga.com",
//		"sendTime": "2016-07-04 00:00:00",
//		"praiseNum": "7",
//		"commentNum": "0",
//		"praiseAuthor": "1",
//		-"user": {
//		"appHeadThumbnail": "",
//		"memberName": "姜明",
//		"memberGrade": "0",
//		"followAuthor": "1"
//		},
//		-"friends": [
//		-{
//		"appHeadThumbnail": ""
//		},
//		-{
//		"appHeadThumbnail": ""
//		},
//		-{
//		"appHeadThumbnail": ""
//		}
//		],
//		"coachComment": "0",
//		"noReadNum": 3
//		}
//		]
//		}
//		}

	public String subjectId,subjectName,videoPhoto,videoFileId,videoUrl,
					sendTime,praiseNum,commentNum,praiseAuthor,coachComment;
	public int noReadNum,totalItems;
	public User user = new User();
	public List<Friends> list_friends = new ArrayList<Friends>();
	
	public static class User{
		public String friendId,appHeadThumbnail,memberName,memberGrade,followAuthor;
	}
	public static class Friends{
		public String appHeadThumbnail;
	}
	@Override
	public String toString() {
		return "AdviceFollowResult [subjectId=" + subjectId + ", subjectName=" + subjectName + ", videoPhoto="
				+ videoPhoto + ", videoFileId=" + videoFileId + ", videoUrl=" + videoUrl + ", sendTime=" + sendTime
				+ ", praiseNum=" + praiseNum + ", commentNum=" + commentNum + ", praiseAuthor=" + praiseAuthor
				+ ", coachComment=" + coachComment + ", noReadNum=" + noReadNum + ", totalItems=" + totalItems
				+ ", user=" + user + ", list_friends=" + list_friends + "]";
	}
	
}
