package com.frame.member.bean;

import java.io.Serializable;

public class NotifyBean extends BaseBean implements Serializable{
	
	// {"type":"司机反馈","detail":"123","amount":"1","sendDate":"2016-01-24 10:42:58","amountMonth":""}
	
	private static final long serialVersionUID = 14513212L;
	
	public String isReaded;
	public String type;
	public String detail;
	public String amount;
	public String sendDate;
	public String amountMonth;
	public String noticeId;
	@Override
	public String toString() {
		return "NotifyBean [isReaded=" + isReaded + ", type=" + type + ", detail=" + detail + ", amount=" + amount
				+ ", sendDate=" + sendDate + ", amountMonth=" + amountMonth + ", noticeId=" + noticeId + "]";
	}
	
	
}
