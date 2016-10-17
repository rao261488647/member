package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.bean.AdviceFindResult.Friends;
import com.frame.member.bean.AdviceFindResult.User;


public class MyVideoResult extends BaseBean {
	
//	{
//    "appHeadOriginal": "",
//    "commentNum": "0",
//    "friends": [],
//    "praiseNum": "0",
//    "sendTime": "2016-10-17 15:25:40",
//    "subjectId": "70",
//    "user": {
//        "appHeadThumbnail": "",
//        "memberGrade": "0",
//        "memberName": "buatapa",
//        "memberUserId": "2274"
//    },
//    "videoFileId": "10065730_c64558f3d02f2eade47c6e3ead3c09cf",
//    "videoUrl": "http://snowcircle-10065730.video.myqcloud.com/1476689137592.mp4.f0.mp4"
//}

	public String subjectId,appHeadOriginal,videoFileId,videoUrl,
					sendTime,praiseNum,commentNum;
	public int totalItems;
	public User user = new User();
	public List<Friends> list_friends = new ArrayList<Friends>();
	
	
}
