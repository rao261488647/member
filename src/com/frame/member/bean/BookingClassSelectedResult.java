package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;


public class BookingClassSelectedResult extends BaseBean {

	public List<String> sdplateChoices = new ArrayList<String>();
	public List<AreaChoices> areaChoices = new ArrayList<BookingClassSelectedResult.AreaChoices>();
	public List<SkifieldChoices> skifieldChoices = new ArrayList<BookingClassSelectedResult.SkifieldChoices>();
	public List<CategoryChoices> categoryChoices = new ArrayList<BookingClassSelectedResult.CategoryChoices>();
	
	public static class AreaChoices{
		public String areaId,areaName;
	}
	public static class SkifieldChoices{
		public String skifieldId,skifieldName;
	}
	public static class CategoryChoices{
		public String categoryId,categoryName;
	}
	
	@Override
	public String toString() {
		return "BookingClassSelectedResult [sdplateChoices=" + sdplateChoices + ", areaChoices=" + areaChoices
				+ ", skifieldChoices=" + skifieldChoices + ", categoryChoices=" + categoryChoices + "]";
	}
	
	
}
