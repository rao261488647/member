package com.frame.member.bean;


import com.frame.member.bean.BaseBean;



public class MyAttentionResult extends BaseBean {
//	{
//		"code": 200,
//		"message": "返回数据成功",
//		-
//		"data": {
//		"myfollow_num": "7",
//		"myfollow_friends": [
//		-
//		{
//		"appHeadThumbnail": "1.jpg"
//		},
//		-
//		
//		]
//		}
//		}
	public String myfollow_num,appHeadThumbnail;

	@Override
	public String toString() {
		return "MyAttentionResult [myfollow_num=" + myfollow_num + ", appHeadThumbnail=" + appHeadThumbnail + "]";
	}



}