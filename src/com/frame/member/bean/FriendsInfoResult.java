package com.frame.member.bean;


import com.frame.member.bean.BaseBean;



public class FriendsInfoResult extends BaseBean {
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		"data": {
//		"user": {
//		"friendId": "10",
//		"appHeadThumbnail": "",
//		"memberName": "姜明",
//		"memberGrade": "0",
//		"memberlLevel": "1",
//		"memberSign": "",
//		"followFriend": "0"
//		}
//		}
//		}
	public String friendId,appHeadThumbnail,memberName,memberGrade,memberlLevel,
					memberSign,followFriend;

	@Override
	public String toString() {
		return "FriendsInfoResult [friendId=" + friendId + ", appHeadThumbnail=" + appHeadThumbnail + ", memberName="
				+ memberName + ", memberGrade=" + memberGrade + ", memberlLevel=" + memberlLevel + ", memberSign="
				+ memberSign + ", followFriend=" + followFriend + "]";
	}


}