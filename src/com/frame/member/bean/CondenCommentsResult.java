package com.frame.member.bean;

import com.frame.member.bean.CondensationResult.Users;

public class CondenCommentsResult extends BaseBean {

	// "status": 200,
	// "comments": [
	// {
	// "uid": "",
	// "nickName": "",
	// "commentContent": "000",
	// "isTalent": "",
	// "profileImaeUrl": "",
	// "targetName":
	// "createDate": "2015-08-15 16:39:25",
	// "user": {
	// "uid": "ff8080814f0168f7014f062a63b10196",
	// "nickName": "hehe",
	// "isTalent": "0",
	// "profileImaeUrl":
	// "http://192.168.1.103:8080/upload/photo/1439634522625.jpg"
	// }
	// },
	// ....
	// ]
	public String commentContent, createDate, targetName;

	public Users user = new Users();

	@Override
	public String toString() {
		return "CondenCommentsResult [commentContent=" + commentContent
				+ ", createDate=" + createDate + ", targetUserId="
				+ targetName + ", user=" + user + "]";
	}


}
