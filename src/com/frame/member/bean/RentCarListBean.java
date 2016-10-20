package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

public class RentCarListBean extends BaseBean {

	public static class RentCarItem{
		public String url;
		public String carType;
		public String deposit;
		public String rentPerPay;
		public String plat;
		public String rentCarId;
		public ArrayList<ContractTerm> contractTermList = new ArrayList();
	}
	
	public List<RentCarItem> rentCarList = new ArrayList<RentCarListBean.RentCarItem>();
	
	
}
