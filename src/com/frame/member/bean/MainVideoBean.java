package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainVideoBean extends BaseBean{

	/**
	 * 首页，视频列表 bean
	 * @author Ron
	 * @date 2016-8-4  下午10:26:53
	 */
	public static class MainVideoCategory implements Serializable{
		private static final long serialVersionUID = 1L;
		public String categoryId;
		public String categoryName;
		public String categoryPhoto;
	}
	
	
	public static class MainVideoResult extends BaseBean{
		
		public List<MainVideoCategory> mainVideoCategoryData = new ArrayList<MainVideoBean.MainVideoCategory>();
	}
	
}
