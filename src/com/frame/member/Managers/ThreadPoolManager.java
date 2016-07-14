package com.frame.member.Managers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 自定义线程池执行全局网络请求
 * 
 * @author Arvin
 * 
 */
public class ThreadPoolManager {

	private ExecutorService service;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();// 系统可用处理器数量
		service = Executors.newFixedThreadPool(num * 2);
	}

	private static final ThreadPoolManager manager = new ThreadPoolManager();

	public static ThreadPoolManager getInstance() {
		return manager;
	}

	public Future<?> addTask(Runnable runnable) {
		return service.submit(runnable);
	}

}
