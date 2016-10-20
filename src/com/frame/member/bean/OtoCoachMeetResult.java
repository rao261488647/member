package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.bean.BaseBean;



public class OtoCoachMeetResult extends BaseBean {
//	{
//		  "code": "200",
//		  "message": "返回数据成功",
//		  "data": {
//		    "coach": {
//		      "coachId": "2",
//		      "headImg": "http://wx.qlogo.cn/mmopen/PiajxSqBRaEIF1uSGia6qeO6t6COGFfB89PBT2MvKU70I2wC9icRm5f5XQwpFq9YeJOPKKup0zEwhRXRq1RYRE5fw/0",
//		      "coachName": "田英梅",
//		      "trainfee": "1580.00",
//		      "coachBadge": "0",
//		      "areaId": "1",
//		      "titleName": "联盟特级",
//		      "skifieldName": "",
//		      "coachStar": "0"
//		    },
//			snowArea": {
//				"areaName": "崇礼"
//			},
//	-		"skifieldChoices": [
//	-			{
//					"skifieldId": "1",
//					"skifieldName": "长城岭滑雪场"
//				},
//	
//			],
//		    "date": [
//		      {
//		        "value": "2016-09-01",
//		        "status": "expire_dates"
//		      },
//			]
	public String coachId,headImg,coachName,trainfee,coachBadge,areaId,titleName,skifieldName,
					coachStar,areaName;
	public List<Date> list_date = new ArrayList<Date>();
	public List<SkifieldChoices> skifieldChoices = new ArrayList<OtoCoachMeetResult.SkifieldChoices>();
	public static class SkifieldChoices{
		public String skifieldId;
		public String skifieldName;
	}
	public static class Date{
		public String value;
		public String status;
	}
	@Override
	public String toString() {
		return "OtoCoachMeetResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", trainfee=" + trainfee + ", coachBadge=" + coachBadge + ", areaId=" + areaId + ", titleName="
				+ titleName + ", skifieldName=" + skifieldName + ", coachStar=" + coachStar + ", areaName=" + areaName
				+ ", list_date=" + list_date + ", skifieldChoices=" + skifieldChoices + "]";
	}
	
	
	
	
}