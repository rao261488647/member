package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainCourseBean extends BaseBean{

	/**
	 * 首页-教程，banner列表 bean
	 * @author Ron
	 * @date 2016-7-7  下午10:26:41
	 */
	public static class MainCourseBanner implements Serializable{
		private static final long serialVersionUID = 1L;
		public String bannerId;
		public String bannerTitle;
		public String bannerPhoto;
		public String bannerLink;
	}
	
	/**
	 * 首页-教程，新闻列表 bean
	 * @author Ron
	 * @date 2016-7-7  下午10:26:41
	 */
	public static class MainCourseNews implements Serializable{
		private static final long serialVersionUID = 1L;
		public String infoId;
		public String infoTitle;
		public String infoIntro;
		public String infoPhoto;
		public String infoIdUrl;
	}
	
	public static class MainCourseResult extends BaseBean{
		
		public List<MainCourseBanner> mainBannerData = new ArrayList<MainCourseBean.MainCourseBanner>();
		public List<MainCourseNews> mainNewsData = new ArrayList<MainCourseBean.MainCourseNews>();
	}
	
}
