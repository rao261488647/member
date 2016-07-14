package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.PersonalListBean;
import com.frame.member.bean.PersonalListBean.PersonalItem;

public class PersonalListParser extends BaseParser<PersonalListBean> {

	@Override
	public PersonalListBean parseJSON(String text) throws JSONException {

		PersonalListBean result = new PersonalListBean();

		JSONObject resultObj = new JSONObject(text);
		if (resultObj != null) {
			result.code = resultObj.optString("code");
			result.message = resultObj.optString("message");
			
			if (TAG_STATUS_SUCC.equals(result.code)) {
				JSONArray dataArray = resultObj.optJSONArray("profile");                                                              
				if(null != dataArray){
					for (int i = 0; i < dataArray.length(); i++) {
						JSONObject dataObj = dataArray.optJSONObject(i);
						PersonalItem personalItem = new PersonalItem();
						
						
						personalItem.name = dataObj.optString("name");
						personalItem.tel = dataObj.optString("tel");
						personalItem.firstGetProDate = dataObj.optString("firstGetProDate");
						personalItem.driveNum = dataObj.optString("driveNum");
						personalItem.isTrainning = dataObj.optString("isTrainning");
						personalItem.speDeiverProperty = dataObj.optString("speDeiverProperty");
						personalItem.regDate = dataObj.optString("regDate");
						
						result.personalList.add(personalItem);
					}
				}
			}
		}

		return result;
	}

}
