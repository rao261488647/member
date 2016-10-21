package com.frame.member.Utils;

import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.text.TextUtils;

import com.frame.member.Parsers.BaseParser;
import com.frame.member.Utils.HttpRequest.RequestMethod;

/***
 * 全局网络请求
 * 
 * @author Arvin
 * 
 */
public class NetUtil {

	private static final String TAG = "NetUtil";

	public static Object post(HttpRequest httpRequest) throws Throwable {

		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(httpRequest.getRequestUri());
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 15000); // 连接超时5000ms
		HttpConnectionParams.setSoTimeout(params, 15000); // 请求超时5000ms
		post.setParams(params);
		Object obj = null;

		if (httpRequest.getParams() != null) {
			MyLog.d(TAG + "---post", httpRequest.getRequestUri() + "?"
					+ httpRequest.getParamWithString());

			Map<String, String> map = httpRequest.getParams();
			ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				BasicNameValuePair pair = new BasicNameValuePair(
						entry.getKey(), entry.getValue());
				pairList.add(pair);
			}

			HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
			post.setEntity(entity);
		}

		HttpResponse response = client.execute(post);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			MyLog.d(TAG, result);
			if (!TextUtils.isEmpty(result)) {
				BaseParser<?> jsonParser = httpRequest.getJsonParser();
				if (jsonParser != null)
					obj = jsonParser.parseJSON(result); //
				else
					obj = result;
			}
			return obj;
		}

		return null;
	}

	public static Object get(HttpRequest httpRequest) throws Throwable {

		String s = httpRequest.getParamWithString();
		String url_get = httpRequest.getRequestUri() + "?" + s;
		if (TextUtils.isEmpty(s))
			url_get = url_get.substring(0, (url_get.toString().length() - 1));

		MyLog.d(TAG + "---get", url_get);
		Object obj = null;

		// HttpGet http_get = new HttpGet(URLEncoder.encode(url_get, "UTF-8"));
		HttpGet http_get = new HttpGet(url_get);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 15000); // 连接超时15000ms
		HttpConnectionParams.setSoTimeout(params, 15000); // 请求超时10000ms
		http_get.setParams(params);

		HttpResponse httpResponse = new DefaultHttpClient().execute(http_get);

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"UTF-8");
			MyLog.d(TAG, result);
			// 在此处添加当服务器返回数据去的登录的逻辑
			if (!TextUtils.isEmpty(result)) {
				BaseParser<?> jsonParser = httpRequest.getJsonParser();
				if (jsonParser != null)
					obj = jsonParser.parseJSON(result); //
				else
					obj = result;
			}
			return obj;
		}

		return null;
	}

	public static Object handleRequest(HttpRequest httpRequest)
			throws Throwable {
		if (httpRequest.getReqMethod() == RequestMethod.get)
			return get(httpRequest);
		else
			return post(httpRequest);
	}

}
