package com.frame.member.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainNotifyBean extends BaseBean{
	
	public int totoalRecord;
	
	public static class Notify implements Serializable{
		private static final long serialVersionUID = 1L;
		public String name;
		public String detail;
		public String date;
	}
	
	public List<Notify> mainpage_data = new ArrayList<MainNotifyBean.Notify>();
	public List<String> mainpage_urls = new ArrayList<String>();

}
