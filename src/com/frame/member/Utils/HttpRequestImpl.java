package com.frame.member.Utils;

import java.util.HashMap;
import java.util.Map;
import android.content.Context;

import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;

/**
 * 用于发送Http请求获取数据，对{@link HttpRequest}的一个实现
 * 
 * @author Arvin
 * 
 */
public class HttpRequestImpl implements HttpRequest {

	private Map<String, String> params = new HashMap<String, String>();
	private BaseParser<?> jsonParser;
	Context context;
	private String do_what;

	private RequestMethod req_method;

	/**
	 * 默认post请求
	 * 
	 * @param demoActivity
	 * @param string
	 * @param parser
	 */
	public HttpRequestImpl(Context demoActivity, String do_what,
			BaseParser<?> parser) {
		this.do_what = do_what;
		this.jsonParser = parser;
		req_method = RequestMethod.post;
	}

	public HttpRequestImpl(Context demoActivity, String do_what,
			BaseParser<?> parser, RequestMethod req_method) {
		this.jsonParser = parser;
		this.do_what = do_what;
		this.req_method = req_method;
	}

	@Override
	public BaseParser<?> getJsonParser() {
		return jsonParser;
	}

	@Override
	public Map<String, String> getParams() {
		return params;
	}

	@Override
	public HttpRequest addParam(String key, String value) {
		this.params.put(new String(key), value);
		// MyLog.d("HttpRequestImpl", "key-->" + key + "     value " + value);

		return this;
	}

	@Override
	public String getRequestUri() {
		//因为目前我的预约是走的v2版本的接口，所以要特别处理一下  by ron
		if("/student/mymeet".equals(do_what)){
			return AppConstants.URI_DEV + "v2" +do_what;
		}
		return AppConstants.URI_DEV + AppConstants.APP_VERSION +do_what;
	}

	@Override
	public String getParamWithString() {
		if (params == null || params.size() < 1)
			return "";

		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		}

		return sb.toString().substring(0, (sb.toString().length() - 1));
	}

	@Override
	public RequestMethod getReqMethod() {
		return req_method;
	}

}
