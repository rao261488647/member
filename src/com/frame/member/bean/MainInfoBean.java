package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainInfoBean extends BaseBean{

	/**
	 * 首页，banner列表 bean
	 * @author Ron
	 * @date 2016-7-7  下午10:26:41
	 */
	public static class MainBanner implements Serializable{
		private static final long serialVersionUID = 1L;
		public String bannerId;
		public String bannerTitle;
		public String bannerPhoto;
		public String bannerLink;
	}
	/**
	 * 首页，课程列表 bean
	 * @author Ron
	 * @date 2016-7-7  下午10:26:53
	 */
	public static class MainRemmendClass implements Serializable{
		private static final long serialVersionUID = 1L;
		public String courseId;
		public String courseName;
		public String courseType;
		public String categoryId;
		public String categoryName;
		public String skifieldId;
		public String skifieldAddr;
		public String beginTime;
		public String overTime;
	}
	
	/**
	 * 首页，新闻列表 bean
	 * @author Ron
	 * @date 2016-7-7  下午10:26:41
	 */
	public static class MainNews implements Serializable{
		private static final long serialVersionUID = 1L;
		public String newsId;
		public String newsTitle;
		public String newsIntro;
		public String newsPhoto;
		public String newsUrl;
	}
	
	public static class MainInfoResult extends BaseBean{
		
		public List<MainBanner> mainBannerData = new ArrayList<MainInfoBean.MainBanner>();
		public List<MainRemmendClass> mainRemmendClassData = new ArrayList<MainInfoBean.MainRemmendClass>();
		public List<MainNews> mainNewsData = new ArrayList<MainInfoBean.MainNews>();
	}
	
}
