package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.frame.member.bean.CoachSpaceResult;
import com.frame.member.bean.CoachSpaceResult.BestComment;

public class CoachSpaceParser extends BaseParser<List<CoachSpaceResult>> {

	@Override
	public List<CoachSpaceResult> parseJSON(String json) throws JSONException {

		List<CoachSpaceResult> list_result = null;
		JSONObject obj = new JSONObject(json);
		if (obj != null) {

			JSONObject obj_Json = obj.optJSONObject("data");
			if (obj_Json != null) {

				JSONArray array = obj_Json.optJSONArray("comments");
				if (array != null && array.length() > 0) {
					list_result = new ArrayList<CoachSpaceResult>();
					for (int i = 0; i < array.length(); i++) {
						CoachSpaceResult result = new CoachSpaceResult();
						JSONObject jsonObject = array.optJSONObject(i);
						result.code = obj.optString("code");
						result.message = obj.optString("message");
						result.subjectId = jsonObject.optString("subjectId");
						result.subjectName = jsonObject.optString("subjectName");
						result.videoPhoto = jsonObject.optString("videoPhoto");
						result.videoFileid = jsonObject.optString("videoFileid");
						result.videoUrl = jsonObject.optString("videoUrl");
						result.videoCode = jsonObject.optString("videoCode");
						result.sendTime = jsonObject.optString("sendTime");
						result.praiseNum = jsonObject.optString("praiseNum");
						result.cPraiseNum = jsonObject.optString("cPraiseNum");
						result.commentTime = jsonObject.optString("commentTime");
						result.praiseCoach = jsonObject.optString("praiseCoach");
						JSONObject obj_json = jsonObject.optJSONObject("bestcomment");
						if (obj_json != null) {
							BestComment bestcomment = new BestComment();
							bestcomment.comment = obj_json.optString("comment");
							bestcomment.time = obj_json.optString("time");
							bestcomment.cPraiseNum = obj_json.optString("cPraiseNum");
							result.bestcomment = bestcomment;
							bestcomment = null;
						}
						JSONObject obj_user = jsonObject.optJSONObject("user");
						if (obj_user != null) {
							result.user.friendId = obj_user.optString("friendId");
							result.user.appHeadThumbnail = obj_user.optString("appHeadThumbnail");
							result.user.memberName = obj_user.optString("memberName");
							result.user.memberGrade = obj_user.optString("memberGrade");

						}
						list_result.add(result);
						result = null;
					}
				}
			}
		}
		return list_result;
	}

}
