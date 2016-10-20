package com.frame.member.bean;


import java.util.ArrayList;
import java.util.List;

import com.frame.member.bean.BaseBean;



public class CoachSearchResult extends BaseBean {
//	{
//	    "code": "200", 
//	    "message": "返回数据成功", 
//	    "totalItems": 1, 
//	    "data": {
//	        "coaches": [
//	            {
//	                "coachId": "31", 
//	                "coachName": "于海涛", 
//	                "headImg": "http://test.flowerski.com/upload/coach/head/31_58071447121542.jpg", 
//	                "coachTitle": "4", 
//	                "badges": [ ], 
//	                "followCoach": "0"
//	            }
//	        ]
//	    }
//	}
	public String coachId,headImg,coachName,coachTitle,followCoach;
	public int totalItems;
	public List<Badges> badges = new ArrayList<Badges>();
	public static class Badges{
		public String badgeId;
		public String badgeName;
	}
	@Override
	public String toString() {
		return "CoachSearchResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", coachTitle=" + coachTitle + ", followCoach=" + followCoach + ", totalItems=" + totalItems
				+ ", badges=" + badges + "]";
	}
	
}