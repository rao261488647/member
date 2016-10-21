package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.bean.CondensationResult.Users;

public class CondenDetailResult extends BaseBean {

	// {
		// "status": 200,
		// "tweet": {
		// "users": [],
		// "likeCount": 0,
		// "isLike": "0",
		// "tweetId": "8a2da3194f6048a7014f62a3b2d50007",
		// "sportType": "滑板",
		// "middleImageurl": [
		//   {
		//    "height": 758,
		//    "imageUrl": "http://192.168.1.111:8080/upload/photo/14423748030.jpg",
		//    "width": 1136
		//	},
		//	{
		//    "height": 669,
		//    "imageUrl": "http://192.168.1.111:8080/upload/photo/14423748031.jpg",
		//    "width": 1008
		//	}
		// ],
		// "isFavorites": "0",
		// "commentsCount": 0,
		// "createDate": "2015-09-06 10:09:50",
		// "user": {
		// "uid": "8a2da3194f6048a7014f62a3b2d50007",
		// "nickName": "龙少1",
		// "isTalent": "0",
		//  "following": 1,
		// "profileImaeUrl":
		// "http://123.57.87.61:8080/upload/photo/1441187904131.jpg"
		// },
		// "viewCount": 15,
		// "tweetContent": ""
		// }
		// }
	public String likeCount, isLike, tweetTitle, tweetId, sportType,
			isFavorites, commentsCount, viewCount, tweetContent, createDate;
	
	public static class User{
		public String uid, nickName, profileImaeUrl, isTalent,following;
	}
	public static class MiddleImageurl{
		public String  imageUrl;
		public int height, width;
	}

	public User user = new User();

	public List<Users> users = new ArrayList<Users>();
	public ArrayList<MiddleImageurl> middleImageurl = new ArrayList<MiddleImageurl>();
	
	@Override
	public String toString() {
		return "CondenDetailResult [likeCount=" + likeCount + ", isLike="
				+ isLike + ", tweetTitle=" + tweetTitle + ", tweetId="
				+ tweetId + ", sportType=" + sportType + ", isFavorites="
				+ isFavorites + ", commentsCount=" + commentsCount
				+ ", viewCount=" + viewCount + ", tweetContent=" + tweetContent
				+ ", createDate=" + createDate + ", user=" + user + ", users="
				+ users + ", middleImageurl=" + middleImageurl + "]";
	}
	
}
