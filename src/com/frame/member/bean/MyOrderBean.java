package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 我的预约bean
 * @author Ron
 * @date 2016-10-16  下午8:40:58
 */
public class MyOrderBean extends BaseBean {

	
	/**
	 * 
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class MyOrder implements Serializable{
		private static final long serialVersionUID = 1L;
		public String season; //年度
		public List<Order> orders; //预约列表
	}
	/**
	 * 预约详情
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class Order implements Serializable{
		private static final long serialVersionUID = 1L;
		public String type; //预约类型
		public String orderId; //预约id
		public String date;//时间
		public OrderCoach coach; //教练属性
		public String skifield;//头像
		public String status;//预约状态  未签到  已签到  已过期 等
	}
	/**
	 * 
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class OrderCoach implements Serializable{
		private static final long serialVersionUID = 1L;
		public String coachId; //教练id
		public String coachHead;//头像
		public String coachName;//教练名字
	}
	/**
	 * 预约-课程
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class OrderClass implements Serializable{
		private static final long serialVersionUID = 1L;
		public String type; //预约类型
		public String orderId; //预约id
		public String courseId; //课程id
		public String beginTime; //开始时间
		public String overTime; //结束时间
		public int hadDay;//天数
		public String categoryName;//类别
		public String courseName;//课程名称
		public String skifield;//头像
		public String status;//预约状态  未签到  已签到  已过期 等
	}
	
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyOrderResult extends BaseBean{
		public List<MyOrder> orderList = new ArrayList<MyOrder>();
	}
	
}
