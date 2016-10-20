package com.frame.member.bean;



public class BookingClassResult extends BaseBean {

	public String courseId;
	public String beginTime;
	public String overTime;
	public String hadDay;
	public String planPrice;
	public String categoryName;
	public String courseName;
	public String sdplate;
	public String courseIntro;
	public String personNumber;
	public String skifieldAddr;
	public String signedUpNum;
	
	@Override
	public String toString() {
		return "BookingClassResult [courseId=" + courseId + ", beginTime=" + beginTime + ", overTime=" + overTime
				+ ", hadDay=" + hadDay + ", planPrice=" + planPrice + ", categoryName=" + categoryName + ", courseName="
				+ courseName + ", sdplate=" + sdplate + ", courseIntro=" + courseIntro + ", personNumber="
				+ personNumber + ", skifieldAddr=" + skifieldAddr + ", signedUpNum=" + signedUpNum + "]";
	}
	
	
	
	
}
