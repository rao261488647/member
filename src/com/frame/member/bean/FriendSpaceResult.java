package com.frame.member.bean;

/**
 * 教练主页bean
 * @author long
 *
 */

public class FriendSpaceResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-"data": {
//		-"videos": [
//		-{
//		"subjectId": "2",
//		"subjectName": "看我飞起来没有",
//		"videoPhoto": "http://www.baidu.com",
//		"videoFileId": "qcloud--VIDEOID",
//		"videoUrl": "http://www.gaga.com",
//		"videoCode": "playcode",
//		"sendTime": "2016-07-04 00:00:00",
//		"praiseNum": "7",
//		"commentNum": "0",
//		"followAuthor": "1",
//		"coachComment": "0",
//		"praiseAuthor": "1"
//		}
//		]
//		}
//		}
	public String subjectId,subjectName,videoFileId,videoPhoto,videoUrl,videoCode,sendTime,
					praiseNum,commentNum,followAuthor,coachComment,praiseAuthor;

	@Override
	public String toString() {
		return "FriendSpaceResult [subjectId=" + subjectId + ", subjectName=" + subjectName + ", videoFileid="
				+ videoFileId + ", videoPhoto=" + videoPhoto + ", videoUrl=" + videoUrl + ", videoCode=" + videoCode
				+ ", sendTime=" + sendTime + ", praiseNum=" + praiseNum + ", commentNum=" + commentNum
				+ ", followAuthor=" + followAuthor + ", coachComment=" + coachComment + ", praiseAuthor=" + praiseAuthor
				+ "]";
	}
	
}
