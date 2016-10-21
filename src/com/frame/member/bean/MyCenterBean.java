package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCenterBean extends BaseBean {

	/**
	 * 个人中心首页--用户信息
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class User implements Serializable{
		private static final long serialVersionUID = 1L;
		public String memberGrade; //等级
		public String memberlLevel;//0:非会员 1:绿卡 2:蓝卡 3:黑卡
		public String appHeadThumbnail;//头像
		public String memberName;//名称
		public String memberSign;//签名
		public String memberPoints;//积分
	}
	/**
	 * 个人中心首页--菜单
	 * @author Ron
	 * @date 2016-9-18  下午11:50:37
	 */
	public static class Menu implements Serializable{
		private static final long serialVersionUID = 1L;
		public String name; //名称
		public int record;//新记录条数
	}
	
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyCenterResult extends BaseBean{
		public User user;
		public List<Menu> menuList = new ArrayList<MyCenterBean.Menu>();
	}
}
