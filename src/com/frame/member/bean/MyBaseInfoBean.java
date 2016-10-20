package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyBaseInfoBean extends BaseBean {

	/**
	 * 个人中心首页--用户基本信息
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class UserInfo implements Serializable{
		private static final long serialVersionUID = 1L;
		public String appHeadThumbnail;//头像
		public String wxNiCheng; //微信昵称
		public String wxAccount;//微信账号
		public String appNiCheng;//昵称
		public String memberName;//姓名
		public String memberSex;//性别
		public String memberBirth;//生日
		public String memberSign;//签名
		public String province;//省
		public String city;//城市
		public String area;//区域
		public String address;//地址
	}
	
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyBaseInfoResult extends BaseBean{
		public UserInfo userInfo;
	}
}
