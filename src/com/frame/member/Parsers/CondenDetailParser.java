package com.frame.member.Parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.CondenDetailResult;
import com.frame.member.bean.CondenDetailResult.MiddleImageurl;
import com.frame.member.bean.CondensationResult.Users;

public class CondenDetailParser extends BaseParser<CondenDetailResult> {

	// {
		// "status": 200,
		// "tweet": {
		// "users": [],
		// "likeCount": 0,
		// "isLike": "0",
		// "tweetId": "8a2da3194f6048a7014f62a3b2d50007",
		// "sportType": "滑板",
		// "middleImageurl": [
		//   {
		//    "height": 758,
		//    "imageUrl": "http://192.168.1.111:8080/upload/photo/14423748030.jpg",
		//    "width": 1136
		//	},
		//	{
		//    "height": 669,
		//    "imageUrl": "http://192.168.1.111:8080/upload/photo/14423748031.jpg",
		//    "width": 1008
		//	}
		// ],
		// "isFavorites": "0",
		// "commentsCount": 0,
		// "createDate": "2015-09-06 10:09:50",
		// "user": {
		// "uid": "8a2da3194f6048a7014f62a3b2d50007",
		// "nickName": "龙少1",
		// "isTalent": "0",
		//  "following": 1,
		// "profileImaeUrl":
		// "http://123.57.87.61:8080/upload/photo/1441187904131.jpg"
		// },
		// "viewCount": 15,
		// "tweetContent": ""
		// }
		// }
	@Override
	public CondenDetailResult parseJSON(String json) throws JSONException {

		CondenDetailResult result = new CondenDetailResult();
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			result.code = obj.optString("code");
			JSONObject object = obj.optJSONObject("tweet");
			result.likeCount = object.optString("likeCount");
			result.isLike = object.optString("isLike");
			result.tweetId = object.optString("tweetId");
			result.createDate = object.optString("createDate");
			result.tweetTitle = object.optString("tweetTitle");
			result.sportType = object.optString("sportType");
			result.isFavorites = object.optString("isFavorites");
			result.commentsCount = object.optString("commentsCount");
			result.viewCount = object.optString("viewCount");
			result.tweetContent = object.optString("tweetContent");
			JSONObject jsonObj = object.optJSONObject("user");
			result.user.uid = jsonObj.optString("uid");
			result.user.nickName = jsonObj.optString("nickName");
			result.user.isTalent = jsonObj.optString("isTalent");
			result.user.profileImaeUrl = jsonObj.optString("profileImaeUrl");
			result.user.following = jsonObj.optString("following");
			JSONArray jsonArray = object.optJSONArray("users");
			if (jsonArray != null && jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject optJsonObj = jsonArray.optJSONObject(i);
					Users user = new Users();
					user.uid = optJsonObj.optString("uid");
					user.nickName = optJsonObj.optString("nickName");
					user.profileImaeUrl = optJsonObj
							.optString("profileImaeUrl");
					user.isTalent = optJsonObj.optString("isTalent");
					result.users.add(user);
					user = null;
				}
			}
			JSONArray optJsonArray = object.optJSONArray("middleImageurl");
			if (optJsonArray != null && optJsonArray.length() > 0) {
				
				for (int i = 0; i < optJsonArray.length(); i++) {
					JSONObject optJSONObject = optJsonArray.optJSONObject(i);
					MiddleImageurl middleImageurl = new MiddleImageurl();
					middleImageurl.height = optJSONObject.optInt("height");
					middleImageurl.imageUrl = optJSONObject.optString("imageUrl");
					middleImageurl.width = optJSONObject.optInt("width");
					result.middleImageurl.add(middleImageurl);
					middleImageurl = null;
//					result.middleImageurl.get(i).imageUrl = optJSONObject.optString("imageUrl");
//					result.middleImageurl.get(i).height = optJSONObject.optString("height");
//					result.middleImageurl.get(i).width = optJSONObject.optString("width");
//					result.middleImageurl.add(object)
				}
			}

		}
		return result;
	}

}
