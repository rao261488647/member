package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class AdviceFindResult extends BaseBean {
	
//	"subjectId": "2",
//    "subjectName": "看我飞起来没有",
//    "videoPhoto": "http://www.baidu.com",
//    "videoFileId": "qcloud--VIDEOID",
//    "videoUrl": "http://www.gaga.com",
//    "videoCode": "playcode",
//    "sendTime": "2016-07-04 00:00:00",
//    "praiseNum": "7",
//    "commentNum": "0",
//    "praiseAuthor": "0",
//    "user": {
//      "friendId": "10",
//      "appHeadThumbnail": "",
//      "memberName": "姜明",
//      "memberGrade": "0",
//      "followAuthor": "0"
//    },
//    "friends": [
//      {
//        "appHeadThumbnail": ""
//      },
//      {
//        "appHeadThumbnail": ""
//      }
//    ],
//    "noReadNum": 2

	public String subjectId,subjectName,videoPhoto,videoFileId,videoUrl,videoCode,
					sendTime,praiseNum,commentNum,praiseAuthor;
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
		return "AdviceFindResult [totalItems=" + totalItems + ", subjectId=" + subjectId + ", subjectName="
				+ subjectName + ", videoPhoto=" + videoPhoto + ", videoFileId=" + videoFileId + ", videoUrl=" + videoUrl
				+ ", videoCode=" + videoCode + ", sendTime=" + sendTime + ", praiseNum=" + praiseNum + ", commentNum="
				+ commentNum + ", praiseAuthor=" + praiseAuthor + ", noReadNum=" + noReadNum + ", user=" + user
				+ ", list_friends=" + list_friends + "]";
	}
	
	
}
