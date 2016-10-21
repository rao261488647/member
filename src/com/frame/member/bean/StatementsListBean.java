package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

public class StatementsListBean extends BaseBean {

	public static class StatementsItem{
		public String money;
		public String plat;
		public String submit_date;
	}
	public List<StatementsItem> statementsList = new ArrayList<StatementsListBean.StatementsItem>();
}
