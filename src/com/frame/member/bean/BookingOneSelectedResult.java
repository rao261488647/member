package com.frame.member.bean;

import java.util.ArrayList;


public class BookingOneSelectedResult extends BaseBean {

	public ArrayList<String> sexChoices = new ArrayList<String>();
	public ArrayList<String> sdplateChoices = new ArrayList<String>();
	public ArrayList<SkifieldChoice> skifieldChoices = new ArrayList<SkifieldChoice>();
	public ArrayList<LevelChoice> levelChoices = new ArrayList<LevelChoice>();
	
	public static class SkifieldChoice{
		public String skifieldId;
		public String skifieldName;
	}
	public static class LevelChoice{
		public String levelId;
		public String levelName;
	}
	@Override
	public String toString() {
		return "BookingOneSelectedResult [sexChoices=" + sexChoices + ", sdplateChoices=" + sdplateChoices
				+ ", skifieldChoices=" + skifieldChoices + ", levelChoices=" + levelChoices + "]";
	}
	
}
