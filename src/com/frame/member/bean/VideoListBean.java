package com.frame.member.bean;

import java.io.Serializable;
import java.util.List;

public class VideoListBean extends BaseBean {

	/**
	 * 首页-视频列表-单视频类别列表
	 * @author Ron
	 * @date 2016-10-14  上午12:01:57
	 */
	public static class MainVideo implements Serializable{
		private static final long serialVersionUID = 1L;
		public String categoryName;// 分类名称
		public String videoFileId; //视频ID
		public String videoName;//视频名称
		public String videoPhoto;//视频封面 
		public String videoIntro;//视频描述
		public String videoUrl;//视频播放地址

	}
	/**
	 * 返回结果集
	 * @author Ron
	 * @date 2016-9-18  下午11:51:26
	 */
	public static class VideoListResult extends BaseBean{
		public List<MainVideo> mainVideoList;
	}
}
