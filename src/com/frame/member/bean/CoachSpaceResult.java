package com.frame.member.bean;

/**
 * 教练主页bean
 * @author long
 *
 */

public class CoachSpaceResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		"data": {
//		"comments": [
//		{
//		"subjectId": "1",
//		"subjectName": "天旋地转",
//		"videoFileid": "qcloud-videoid",
//		"videoPhoto": "http://img3.qianzhan123.com/news/201309/26/20130926-a888fc7058bcca72.jpg",
//		"videoUrl": "http://pic8.nipic.com/20100723/4943220_075234451520_2.jpg",
//		"videoCode": "playcode",
//		"sendTime": "2016-07-05 10:41:33",
//		"praiseNum": "44",
//		"cPraiseNum": "19",
//		"commentTime": "2016-07-05 10:52:23",
//		"praiseCoach": "0"
//		-
//		"bestcomment": {
//		"comment": "好神奇的学员，超出我多年来的教学经验",
//		"time": "2016-07-05 10:52:23",
//		"cPraiseNum": "19"
//		},
//		"user": {
//		"friendId": "23",
//		"appHeadThumbnail": "",
//		"memberName": "",
//		"memberGrade": "0"
//		},
//		"followStudent": "0"
//		}
//		]
//		}
//		}

	public String subjectId,subjectName,videoFileid,videoPhoto,videoUrl,videoCode,sendTime,
					praiseNum,cPraiseNum,commentTime,praiseCoach,followStudent;
	public BestComment bestcomment = new BestComment();
	public User user = new User();
	
	public static class BestComment{
		public String comment,time,cPraiseNum;
	}
	public static class User{
		public String friendId,appHeadThumbnail,memberName,memberGrade;
	}
	
	@Override
	public String toString() {
		return "CoachSpaceResult [subjectId=" + subjectId + ", subjectName=" + subjectName + ", videoFileid="
				+ videoFileid + ", videoPhoto=" + videoPhoto + ", videoUrl=" + videoUrl + ", videoCode=" + videoCode
				+ ", sendTime=" + sendTime + ", praiseNum=" + praiseNum + ", cPraiseNum=" + cPraiseNum
				+ ", commentTime=" + commentTime + ", praiseCoach=" + praiseCoach + ", followStudent=" + followStudent
				+ "]";
	}
	
	
}
