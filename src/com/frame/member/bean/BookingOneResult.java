package com.frame.member.bean;

import java.util.ArrayList;


public class BookingOneResult extends BaseBean {

	public String totalItems;
	public ArrayList<String> sexChoices = new ArrayList<String>();
	public ArrayList<String> sdplateChoices = new ArrayList<String>();
	public ArrayList<SkifieldChoice> skifieldChoices = new ArrayList<SkifieldChoice>();
	public ArrayList<LevelChoice> levelChoices = new ArrayList<LevelChoice>();
	public ArrayList<Coach> coaches = new ArrayList<Coach>();
	
	public static class SkifieldChoice{
		public String skifieldId;
		public String skifieldName;
	}
	public static class LevelChoice{
		public String levelId;
		public String levelName;
	}
	public static class Coach{
		public String coachId,coachName,headImg,badgeName,levelName;
	}
	@Override
	public String toString() {
		return "BookingOneResult [totalItems=" + totalItems + ", sexChoices="
				+ sexChoices + ", sdplateChoices=" + sdplateChoices
				+ ", skifieldChoices=" + skifieldChoices + ", levelChoices="
				+ levelChoices + ", coaches=" + coaches + "]";
	}
	
	
}
