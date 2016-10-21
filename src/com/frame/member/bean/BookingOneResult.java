package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class BookingOneResult extends BaseBean {

	public String totalItems;
	public ArrayList<Coach> coaches = new ArrayList<Coach>();
	
	public static class Coach{
		public String coachId,coachName,headImg,coachBadge,levelName,meetNum;
		public float goal;
		public List<Badges> badges = new ArrayList<Badges>();
		
	}
	public static class Badges{
		public String badgeId;
		public String badgeName;
	}

	

	
	
	
	
}
