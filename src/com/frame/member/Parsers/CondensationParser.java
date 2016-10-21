package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;

import com.frame.member.AppConstants.AppConstants;
import com.frame.member.bean.CondensationResult;
public class CondensationParser extends BaseParser<List<CondensationResult>> {

	// {
	// "status": 200,
	// "tweets": [
	// {
	// "uid": "",
	// "imagesSource": [
	// {
	// "thumbnallImageurl": "111",
	// "middleImageurl": "32131"
	// }
	// ],
	// "thumbnallImageurl": "",
	// "likeCount": "1",
	// "videoUrl": "111",
	// "middleImageurl": "",
	// "commentsCount": "1",
	// "tweetType": "111",
	// "isLike": "0",
	// "nickName": "",
	// "sportType": "3",
	// "tweetId": "231231",
	// "profileImaeUrl": "",
	// "isTalent": "",
	// "createDate": "2015-07-21 09:35:16",
	// "user": {
	// "uid": "402881eb4e6741d4014e6743e7100003",
	// "nickName": "123",
	// "isTalent": null,
	// "profileImaeUrl": null
	// },
	// "viewCount": "0",
	// "tweetContent": "111"
	// },
	// ......
	// ]
	// }
	@Override
	public List<CondensationResult> parseJSON(String json) throws JSONException {
		List<CondensationResult> resultList = null;
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			JSONArray jsonObjs = obj.optJSONArray("tweets");
			if (jsonObjs != null && jsonObjs.length() > 0) {
				resultList = new ArrayList<CondensationResult>();
				for (int i = 0; i < jsonObjs.length(); i++) {
					JSONObject jsonObj = jsonObjs.optJSONObject(i);
					if (jsonObj != null) {
						CondensationResult result = new CondensationResult();
						result.code = obj.optString("code");
						result.uid = jsonObj.optString("uid");
						JSONArray imageArray = jsonObj
								.optJSONArray("imagesSource");
						// for (int j = 0; j < imageArray.length(); j++) {
						// JSONObject imageObj=imageArray.optJSONObject(j);
						// ImagesSource imagesSource=new ImagesSource();
						// imagesSource.thumbnallImageurl=imageObj.optString("thumbnallImageurl");
						// imagesSource.middleImageurl=imageObj.optString("middleImageurl");
						// result.imagesSource.add(imagesSource);
						// imagesSource=null;
						// }
						result.thumbnallImageurl = jsonObj
								.optString("thumbnallImageurl");
						result.likeCount = jsonObj.optString("likeCount");
						result.videoUrl = jsonObj.optString("videoUrl");
						result.middleImageurl = jsonObj
								.optString("middleImageurl");
						result.commentsCount = jsonObj
								.optString("commentsCount");
						result.likeCount = jsonObj.optString("likeCount");
						result.tweetTitle = jsonObj.optString("tweetTitle");
						result.commentsCount = jsonObj
								.optString("commentsCount");
						result.tweetType = jsonObj.optString("tweetType");
						result.isLike = jsonObj.optString("isLike");
						result.nickName = jsonObj.optString("nickName");
						result.sportType = jsonObj.optString("sportType");
						result.tweetId = jsonObj.optString("tweetId");
						result.profileImaeUrl = jsonObj
								.optString("profileImaeUrl");
						result.isTalent = jsonObj.optString("isTalent");
						result.createDate = jsonObj.optString("createDate");
						result.viewCount = jsonObj.optString("viewCount");
						result.tweetContent = jsonObj.optString("tweetContent");

						JSONObject userObj = jsonObj.optJSONObject("user");
						result.user.uid = userObj.optString("uid");
						result.user.nickName = userObj.optString("nickName");
						result.user.profileImaeUrl = userObj
									.optString("profileImaeUrl");
						result.user.isTalent = userObj.optString("isTalent");

						resultList.add(result);
						result = null;
					}
				}
			}
		}
		return resultList;
	}

	//

}
