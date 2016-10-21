package com.frame.member.bean;

public class ClassDetailResult extends BaseBean {
	
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		-"data": {
//		-"class": {
//		"courseId": "6",
//		"beginTime": "2015-11-23",
//		"overTime": "2015-11-27",
//		"hadDay": "4",
//		"planPrice": "50.00",
//		"courseName": "国职滑雪教练补考通道——理论知识",
//		"sdplate": "双板",
//		"courseIntro": "补考专用——理论知识",
//		"personNumber": "20",
//		"videoPhoto": "classvideo_17721471438200.jpg",
//		"videoFileid": "14651978969265585159",
//		"skifieldId": "1",
//		"skifieldAddr": "张家口市崇礼县西湾子镇小石夭村和平森林公园内",
//		"videoUrl": null,
//		"signedUpNum": "0",
//		"originalPrice": "50.00",
//		"discountPrice": "50",
//		"goal": 3,
//		"isSigned": "0",
//		"collect": "0"
//		}
//		}
//		}

	public String courseId,beginTime,overTime,hadDay,planPrice,courseName,sdplate,
			courseIntro,personNumber,videoPhoto,videoFileid,skifieldId,skifieldAddr,
			videoUrl,signedUpNum,originalPrice,discountPrice,isSigned,collect;
	public int goal;

	@Override
	public String toString() {
		return "ClassDetailResult [courseId=" + courseId + ", beginTime=" + beginTime + ", overTime=" + overTime
				+ ", hadDay=" + hadDay + ", planPrice=" + planPrice + ", courseName=" + courseName + ", sdplate="
				+ sdplate + ", courseIntro=" + courseIntro + ", personNumber=" + personNumber + ", videoPhoto="
				+ videoPhoto + ", videoFileid=" + videoFileid + ", skifieldId=" + skifieldId + ", skifieldAddr="
				+ skifieldAddr + ", videoUrl=" + videoUrl + ", signedUpNum=" + signedUpNum + ", originalPrice="
				+ originalPrice + ", discountPrice=" + discountPrice + ", goal=" + goal + ", isSigned=" + isSigned
				+ ", collect=" + collect + "]";
	}
	
	
	
	
}
