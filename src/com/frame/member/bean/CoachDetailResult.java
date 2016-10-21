package com.frame.member.bean;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.bean.BaseBean;
import com.frame.member.bean.CoachSearchResult.Badges;



public class CoachDetailResult extends BaseBean {
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-"data": {
//		-"coach": {
//		"coachId": "34",
//		"headImg": "",
//		"coachName": "郑华",
//		"teacher": "专职教师",
//		"titlename": "",
//	"meetNum": "84", 
//		"levelName": "一级考评员",
//		"trainfee": "1800.00",
//		"skifieldName": "",
//	 "badges": [ ], 
//		"goal": 5,
//		"intro": "平时，郑华喜欢在房间看书或钓鱼，不熟悉他的人会以为他只是一个安静的要美男子。但是，一旦到了雪上，他顿时变得疯狂起来，自由驰骋于白雪之间，帅气、洒脱，充满激情。雪上教学的郑华总是激情满满，高超、全面的滑雪技巧，细心、体贴的教学，又非常善于与学员沟通让他深爱学员的喜爱",
//		"honor": "",
//		"specialty": "专业高山滑雪运动员\r\n尤其擅长专业卡宾、旗门动作教学~",
//		-"video": {
//		"videoPhoto": "34_54631471436525.jpg",
//		"videoFileId": "14651978969267214414",
//		"videoUrl": "http://200016032.vod.myqcloud.com/200016032_6526ecc86e8b11e6a64dc32cafcf1781.f20.mp4"
//		},
//		-"photo": [
//		-{
//		"photoURL": "51_s1_20151121131934.jpeg"
//		},
//		-{
//		"photoURL": "51_s2_20151121131934.jpeg"
//		},
//		
//		],
//		"isSigned": "1",
//		"collect": "0"
//		}
//		}
//		}
	public String coachId,headImg,coachName,teacher,titlename,levelName,
				trainfee,skifieldName,intro,honor,specialty,isSigned,collect,
				videoPhoto,videoFileId,videoUrl,areaName,meetNum;
	public int goal;
	public List<Photo> photo = new ArrayList<CoachDetailResult.Photo>();
	public static class Photo{
		public String photoURL;
	}
	public List<Badges> badges = new ArrayList<CoachSearchResult.Badges>();
	@Override
	public String toString() {
		return "CoachDetailResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", teacher=" + teacher + ", titlename=" + titlename + ", levelName=" + levelName + ", trainfee="
				+ trainfee + ", skifieldName=" + skifieldName + ", intro=" + intro + ", honor=" + honor + ", specialty="
				+ specialty + ", isSigned=" + isSigned + ", collect=" + collect + ", videoPhoto=" + videoPhoto
				+ ", videoFileId=" + videoFileId + ", videoUrl=" + videoUrl + ", areaName=" + areaName + ", meetNum="
				+ meetNum + ", goal=" + goal + ", photo=" + photo + ", badges=" + badges + "]";
	}
	
	
	
}