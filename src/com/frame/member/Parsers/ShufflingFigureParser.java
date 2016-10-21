package com.frame.member.Parsers;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.frame.member.bean.ShufflingFigureResult;

public class ShufflingFigureParser extends BaseParser<List<ShufflingFigureResult>> {

	@Override
	public List<ShufflingFigureResult> parseJSON(String json) throws JSONException {

		List<ShufflingFigureResult> resultList = null;
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			JSONArray jsonObjs = obj.optJSONArray("feeds");
			if (jsonObjs != null && jsonObjs.length() > 0) {
				resultList = new ArrayList<ShufflingFigureResult>();
				for (int i = 0; i < jsonObjs.length(); i++) {
					JSONObject jsonObj = jsonObjs.optJSONObject(i);
					if (jsonObj != null) {
						ShufflingFigureResult result = new ShufflingFigureResult();
						result.code = obj.optString("code");
						result.feedTitle = jsonObj.optString("feedTitle");
						result.cover = jsonObj.optString("cover");
						result.source = jsonObj.optString("source");
						result.type = jsonObj.optString("type");

						resultList.add(result);
						result = null;
					}
				}
			}
		}

		return resultList;
	}
}
