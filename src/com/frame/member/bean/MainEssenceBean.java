package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页-精华bean
 * @author raopeng
 *
 */
public class MainEssenceBean extends BaseBean {

	/**
	 * 
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class EssenceInfo implements Serializable{
		private static final long serialVersionUID = 1L;
		public String subjectId; //帖子ID
		public String subjectName; //帖子标题
		public String videoFileId;//视频ID
		public String videoPhoto;//视频封面
		public String videoUrl;//转码后的视频地址
		public String sendTime;//上传时间
		public int praiseNum;//点赞次数
		public int commentNum;//回复次数
		public String praiseAuthor; //是否点赞学友 1或0
		public EssenceUser user; //作者
		public List<EssenceStudent> students; //点赞学员
		public List<EssenceBestComment> bestComment; //最佳评论
	}
	/**
	 * 精华，作者
	 * @author raopeng
	 *
	 */
	public static class EssenceUser implements Serializable{
		private static final long serialVersionUID = 1L;
		public String friendId; //作者ID
		public String appHeadThumbnail; //APP用户缩略头像
		public String memberName; //会员名称
		public String memberGrade; //会员级数
		public String followAuthor; //作者
	}
	/**
	 * 点赞雪友
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class EssenceStudent implements Serializable{
		private static final long serialVersionUID = 1L;
		public String appHeadThumbnail; //APP用户缩略头像
	}
	/**
	 * 最佳评论
	 * @author Ron
	 * @date 2016-9-18  下午11:48:17
	 */
	public static class EssenceBestComment implements Serializable{
		private static final long serialVersionUID = 1L;
		public String coachName; //教练名字
		public String headImg; //APP用户缩略头像
		public String levelname; //教练级别
		public String commentcontent; //点评内容
		public String praiseCoach; //是否点赞教练 1或0
		public List<Badge> badges; //教练徽章
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
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class EssenceResult extends BaseBean{
		public List<EssenceInfo> essenceInfoList = new ArrayList<EssenceInfo>();
	}
	

}
