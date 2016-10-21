package com.frame.member.Parsers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 抽象parser 子类需重写parseJSON方法解析 封装 返回数据
 * 
 * @author Arvin
 * 
 * @param <T>
 */
public abstract class BaseParser<T> {

	protected final String TAG_STATUS_SUCC = "200";
	
	public abstract T parseJSON(String text) throws JSONException;

	/**
	 * 解析返回结果response字段 待定
	 * 
	 * @param text
	 * @return
	 * @throws JSONException
	 */
	public String checkResponse(String text) throws JSONException {
		if (text == null) {
			return null;
		}

		JSONObject jsonObject = new JSONObject(text);
		String result = jsonObject.getString("response");
		if (result != null && !result.equals("error")) {
			return result;
		}
		return null;
	}
}
