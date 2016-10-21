package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class StudentCommentsResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-
//		"data": {
//		o-
//		"students": [
//		-
//		{
//		"friendId": "13",
//		"appHeadThumbnail": "",
//		"memberName": "",
//		   "memberLevel":"",
//		"commentContent": "好神奇的学员，超出我多年来的教学经验",
//		"commentTime": "2016-07-05"
//		"praiseAuthor": "0"
//		}
//		]
//		}
//		}

	public String friendId,appHeadThumbnail,memberName,memberLevel,commentContent,commentTime,praiseAuthor;

	@Override
	public String toString() {
		return "StudentCommentsResult [friendId=" + friendId + ", appHeadThumbnail=" + appHeadThumbnail
				+ ", memberName=" + memberName + ", memberLevel=" + memberLevel + ", commentContent=" + commentContent
				+ ", commentTime=" + commentTime + ", praiseAuthor=" + praiseAuthor + "]";
	}

	
	
}
