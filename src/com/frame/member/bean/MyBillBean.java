package com.frame.member.bean;

import java.io.Serializable;
import java.util.List;

public class MyBillBean extends BaseBean {

	/**
	 * 消费流水bean
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class Consumption implements Serializable{
		private static final long serialVersionUID = 1L;
		public String year;//年度
		public int totalAmount; //总金额
		public List<BillInfo> info; //详情
	}
	
	public static class BillInfo implements Serializable{
		private static final long serialVersionUID = 1L;
		public String consumptionType;// 1 一对一 2班课 其它则为空
		public String appHeadThumbnail; //头像地址
		public String money;//金额
		public String remarks;//备注
		public Long time;//时间，毫秒数
		public String unsubscribe;//是否取消   0：否， 1：是
	}
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class MyBillResult extends BaseBean{
		public List<Consumption> consumptionList;
	}
}
