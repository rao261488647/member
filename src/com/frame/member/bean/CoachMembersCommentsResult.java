package com.frame.member.bean;



public class CoachMembersCommentsResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		"data": {
//		"students": [
//		-
//		{
//		"friendId": "13",
//		"appHeadThumbnail": "",
//		"memberName": "",
//		"commentContent": "好神奇的学员，超出我多年来的教学经验",
//		"commentTime": "2016-07-05 10:52:23"
//		"praiseAuthor": "0"
//		}
//		]
//		}
//		}
	
	public String friendId,appHeadThumbnail,memberName,commentContent,
					commentTime,praiseAuthor;

	@Override
	public String toString() {
		return "CoachMembersCommentsResult [friendId=" + friendId + ", appHeadThumbnail=" + appHeadThumbnail
				+ ", memberName=" + memberName + ", commentContent=" + commentContent + ", commentTime=" + commentTime
				+ ", praiseAuthor=" + praiseAuthor + "]";
	}

	
}
