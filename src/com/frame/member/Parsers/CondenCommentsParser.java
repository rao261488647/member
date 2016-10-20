package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.CondenCommentsResult;

public class CondenCommentsParser extends
		BaseParser<List<CondenCommentsResult>> {

	// {
	// "status": 200,
	// "comments": [
	// {
	// "uid": "",
	// "nickName": "",
	// "commentContent": "000",
	// "isTalent": "",
	//targetName
	// "profileImaeUrl": "",
	// "createDate": "2015-08-15 16:39:25",
	// "user": {
	// "uid": "ff8080814f0168f7014f062a63b10196",
	// "nickName": "hehe",
	// "isTalent": "0",
	// "profileImaeUrl":
	// "http://192.168.1.103:8080/upload/photo/1439634522625.jpg"
	// }
	// },
	// .....
	// ]
	@Override
	public List<CondenCommentsResult> parseJSON(String json)
			throws JSONException {

		List<CondenCommentsResult> result = new ArrayList<CondenCommentsResult>();
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			JSONArray JSONarray = obj.optJSONArray("comments");
			if (JSONarray != null && JSONarray.length() > 0) {
				for (int i = 0; i < JSONarray.length(); i++) {
					JSONObject optJsonObj = JSONarray.optJSONObject(i);
					if (optJsonObj != null) {
						CondenCommentsResult conden_result = new CondenCommentsResult();
						conden_result.code = obj.optString("code");
						conden_result.commentContent = optJsonObj
								.optString("commentContent");
						conden_result.createDate = optJsonObj
								.optString("createDate");
						conden_result.targetName = optJsonObj
								.optString("targetName");
						JSONObject user = optJsonObj.optJSONObject("user");
						conden_result.user.uid = user.optString("uid");
						conden_result.user.nickName = user
								.optString("nickName");
						conden_result.user.profileImaeUrl = user
								.optString("profileImaeUrl");
						conden_result.user.isTalent = user
								.optString("isTalent");
						conden_result.code = obj.optString("code");
						result.add(conden_result);
						conden_result = null;
					}

				}

			}

		}
		return result;

	}

}
