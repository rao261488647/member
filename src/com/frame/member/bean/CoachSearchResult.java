package com.frame.member.bean;


import com.frame.member.bean.BaseBean;



public class CoachSearchResult extends BaseBean {
//	{
//		"code": "200",
//		"message": "返回数据成功",
//		"totalItems": 1,
//		-"data": {
//		-"coaches": [
//		-{
//		"coachId": "24",
//		"coachName": "孙小元",
//		"headImg": "http://wx.qlogo.cn/mmopen/PVJAFu3VTicYicAOt6qThBZw3TE6mt6DZ4YP0klibQdOPmYcyl0RiaN4NdrabsSaKa268AL60ick3Liar2Kl1OcNouNCoxdUJXEjzc/0",
//		"coachTitle": "5",
//		"coachBadge": "0"
//		}
//		]
//		}
//		}
	public String coachId,headImg,coachName,coachTitle,coachBadge;
	public int totalItems;
	@Override
	public String toString() {
		return "CoachSearchResult [coachId=" + coachId + ", headImg=" + headImg + ", coachName=" + coachName
				+ ", coachTitle=" + coachTitle + ", coachBadge=" + coachBadge + ", totalItems=" + totalItems + "]";
	}

	

	

}