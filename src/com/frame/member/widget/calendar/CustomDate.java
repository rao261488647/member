package com.frame.member.widget.calendar;

import java.io.Serializable;
public class CustomDate implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	public int year;
	public int month;
	public int day;
	public int week;
	public String[] month_eng = {"Jan","Feb","Mar","Apr",
			"May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
	
	public CustomDate(int year,int month,int day){
		if(month > 12){
			month = 1;
			year++;
		}else if(month <1){
			month = 12;
			year--;
		}
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public CustomDate(){
		this.year = DateUtil.getYear();
		this.month = DateUtil.getMonth();
		this.day = DateUtil.getCurrentMonthDay();
	}
	
	public static CustomDate modifiDayForObject(CustomDate date,int day){
		CustomDate modifiDate = new CustomDate(date.year,date.month,day);
		return modifiDate;
	}
	@Override
	public String toString() {
		String str_month = month+"";
		String str_day = day+"";
		if(month < 10){
			 str_month = "0"+month; 
		}
		if(day < 10){
			 str_day = "0"+day;
		}
		return year+"-"+str_month+"-"+str_day;
	}
	//换一种显示格式
	public String toSecString(){
		String str_month = month+"";
		String str_day = day+"";
		if(month < 10){
			 str_month = "0"+month; 
		}
		if(day < 10){
			 str_day = "0"+day;
		}
		return year+"."+str_month+"."+str_day;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof CustomDate){
			if(year == ((CustomDate)o).year && month == ((CustomDate)o).month 
					&& day == ((CustomDate)o).day){
				return true;
			}
		}
		return false;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

}
