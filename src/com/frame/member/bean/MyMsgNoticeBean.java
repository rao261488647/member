package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyMsgNoticeBean extends BaseBean{

	/**
	 * 我的通知
	 * @author Ron
	 * @date 2016-10-10  上午1:12:16
	 */
	public static class Notice implements Serializable{
		private static final long serialVersionUID = 1L;
		public String noticeId;
		public String noticeTitle;
		public String noticeContent;
		public String noticeTime;
		public int isRead;
	}
	
	public static class MyMsgNoticeResult extends BaseBean{
		
		public List<Notice> noticeList = new ArrayList<MyMsgNoticeBean.Notice>();
	}
	
}
