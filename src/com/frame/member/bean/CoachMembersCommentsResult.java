package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

public class CoachMembersCommentsResult extends BaseBean {
	
//	{
//	    "code": "200", 
//	    "message": "返回数据成功", 
//	    "data": {
//	        "comment": [
//	            {
//	                "appHeadThumbnail": "", 
//	                "memberName": "", 
//	                "goal": "5", 
//	                "evaluateContent": "很喜欢教练的教学方式！", 
//	                "evaluateTime": "2016-08-31 19:21:45", 
//	                "tags": [
//	                    {
//	                        "name": "牛人"
//	                    }, 
//	                    {
//	                        "name": "高手"
//	                    }, 
//	                    {
//	                        "name": "好开心"
//	                    }
//	                ]
//	            }
//	        ]
//	    }
//	}
	
	public String friendId,appHeadThumbnail,memberName,evaluateContent,
					evaluateTime;
	public List<Tag> tags = new ArrayList<Tag>();

	public static class Tag{
		public String  name;
	}

	@Override
	public String toString() {
		return "CoachMembersCommentsResult [friendId=" + friendId + ", appHeadThumbnail=" + appHeadThumbnail
				+ ", memberName=" + memberName + ", evaluateContent=" + evaluateContent + ", evaluateTime="
				+ evaluateTime + ", tags=" + tags + "]";
	}
	
}
