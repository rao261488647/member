package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 消息 bean
 * @author raopeng
 *
 */
public class MyMsgBean extends BaseBean{

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
	/**
	 * 评论
	 * @author Ron
	 * @date 2016-10-10  上午1:12:16
	 */
	public static class Comment implements Serializable{
		private static final long serialVersionUID = 1L;
		public String appHeadThumbnail; //评论者头像地址
		public String memberName;//评论者姓名
		public String videoPhoto;// // 视频封面
		public String videoFileId; //视频id
		public String videoUrl; //转码后的视频地址
		public String noticeTime; //通知时间
		public int isRead; //是否已读 0未读 1已读
		public int isCoachComment; //是否是教练评论 1是，0不是
	}
	/**
	 * 赞消息
	 * @author Ron
	 * @date 2016-10-10  上午1:12:16
	 */
	public static class Zan implements Serializable{
		private static final long serialVersionUID = 1L;
		public String appHeadThumbnail; //评论者头像地址
		public String memberName;//评论者姓名
		public String videoPhoto;// // 视频封面
		public String videoFileId; //视频id
		public String videoUrl; //转码后的视频地址
		public String noticeTime; //通知时间
		public int isRead; //是否已读 0未读 1已读
		public int isCoachComment; //是否是教练评论 1是，0不是
	}
	
	public static class MyMsgNoticeResult extends BaseBean{
		
		public List<Notice> noticeList = new ArrayList<MyMsgBean.Notice>();
	}
	public static class MyMsgCommentResult extends BaseBean{
		
		public List<Comment> commentList = new ArrayList<MyMsgBean.Comment>();
	}
	public static class MyMsgZanResult extends BaseBean{
	
		public List<Zan> zanList = new ArrayList<MyMsgBean.Zan>();
	}
	
}
