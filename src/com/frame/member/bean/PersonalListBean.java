package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

public class PersonalListBean extends BaseBean {

	public static class PersonalItem{
		public String name;
		public String tel;
		public String firstGetProDate;
		public String driveNum;
		public String isTrainning;
		public String speDeiverProperty;
		public String regDate;
	}
	public List<PersonalItem> personalList = new ArrayList<PersonalListBean.PersonalItem>();
}
