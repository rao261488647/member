package com.frame.member.Utils;

import java.util.Map;

import com.frame.member.Parsers.BaseParser;

/**
 * 对Http请求进行封装，如果需求不一样，可另写一个类实现这个接口
 * 
 * @author Arvin
 */
public interface HttpRequest {

	// /**
	// * 获取当前请求的上下文
	// *
	// * @return
	// */
	// public abstract Context getContext();

	/**
	 * 获取请求的URI
	 * 
	 * @return
	 */
	public abstract String getRequestUri();

	/**
	 * 获取解析请求结果的Parser
	 * 
	 * @return
	 */
	public abstract BaseParser<?> getJsonParser();

	/**
	 * 获取请求参数的Map
	 * 
	 * @return
	 */
	public abstract Map<String, String> getParams();

	/**
	 * 添加请求参数（可用链式编程,如addParam("key", "value").addParam("key", "value")
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public abstract HttpRequest addParam(String key, String value);

	/**
	 * 获取请求参数的String串，方便打印Log
	 * 
	 * @return
	 */
	public abstract String getParamWithString();

	public abstract RequestMethod getReqMethod();

	public enum RequestMethod {
		get, post;
	}

}