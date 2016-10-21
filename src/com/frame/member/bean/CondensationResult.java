package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

public class CondensationResult extends BaseBean {

	// {
	// "status": 200,
	// "tweets": [
	// {
	// "uid": "",
	// "thumbnallImageurl": "",
	// "likeCount": "1",
	// "videoUrl": "111",
	// "middleImageurl": "",
	// "commentsCount": "1",
	// "tweetType": "111",
	// "tweetTitle":""
	// "isLike": "0",
	// "nickName": "",
	// "sportType": "3",
	// "tweetId": "231231",
	// "profileImaeUrl": "",
	// "isTalent": "",
	// "createDate": "2015-07-21 09:35:16",
	// "user": {
	// "uid": "402881eb4e6741d4014e6743e7100003",
	// "nickName": "123",
	// "isTalent": null,
	// "profileImaeUrl": null
	// },
	// "viewCount": "0",
	// "tweetContent": "111"
	// },
	// ......
	// ]
	// }

	public String uid, thumbnallImageurl, likeCount, videoUrl, middleImageurl,
			commentsCount, tweetType, isLike, nickName, sportType, tweetId,
			profileImaeUrl, isTalent, createDate, viewCount, tweetContent,
			tweetTitle;
	public List<ImagesSource> imagesSource = new ArrayList<ImagesSource>();
	public Users user = new Users();

	public static class ImagesSource {
		public String thumbnallImageurl;
		public String middleImageurl;
	}

	public static class Users {
		public String uid, nickName, profileImaeUrl, isTalent;
	}

	@Override
	public String toString() {
		return "CondensationResult [uid=" + uid + ", thumbnallImageurl="
				+ thumbnallImageurl + ", likeCount=" + likeCount
				+ ", videoUrl=" + videoUrl + ", middleImageurl="
				+ middleImageurl + ", commentsCount=" + commentsCount
				+ ", tweetType=" + tweetType + ", isLike=" + isLike
				+ ", nickName=" + nickName + ", sportType=" + sportType
				+ ", tweetId=" + tweetId + ", profileImaeUrl=" + profileImaeUrl
				+ ", isTalent=" + isTalent + ", createDate=" + createDate
				+ ", viewCount=" + viewCount + ", tweetContent=" + tweetContent
				+ ", tweetTitle=" + tweetTitle + ", imagesSource="
				+ imagesSource + ", user=" + user + "]";
	}

}
