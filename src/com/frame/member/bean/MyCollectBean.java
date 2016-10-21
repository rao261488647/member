package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCollectBean extends BaseBean {

	/**
	 * 我的收藏-教练
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class CollectCoach implements Serializable{
		private static final long serialVersionUID = 1L;
		public String collectId; //收藏id
		public String coachId; //教练id
		public String coachName;//教练名字
		public String headImg;//头像
		public String coachLevel;//国家认证级别
		public String hshLevel;//郝世花级别
		public int istutor;//专职导师,0:不是 1:是
		public String coachTitle;//教练职称
		public String specialty; //擅长
		public List<Badge> badges; //徽章集合
	}
	/**
	 * 教练的徽章
	 * @author raopeng
	 *
	 */
	public static class Badge implements Serializable{
		private static final long serialVersionUID = 1L;
		public String badgeId; //徽章id
		public String badgeName; //徽章名字
	}
	/**
	 * 我的收藏-课程
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class CollectClass implements Serializable{
		private static final long serialVersionUID = 1L;
		public String collectId; //收藏id
		public String courseId; //课程id
		public String courseName;//课程名称
		public String courseIntro;//课程简介
		public String categoryName;//类别
		public String sdPlate;//单双版
		public int personNumber;//报名人数
		public String planPrice;//价格
		public String skifieldAddr;//雪场地址
		public int signedUpNum;//报名人数
		public int hadDay;//天数
		public String beginTime; //开始时间
		public String overTime; //开始时间
		public String signedUpStatus; //课程状态
	}
	
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyCollectCoachResult extends BaseBean{
		public List<CollectCoach> collectCoachList = new ArrayList<CollectCoach>();
	}
	

	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyCollectClassResult extends BaseBean{
		public List<CollectClass> collectClassList = new ArrayList<CollectClass>();
	}
}
