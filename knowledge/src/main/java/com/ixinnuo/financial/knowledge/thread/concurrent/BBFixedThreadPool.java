package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * 
 * @author 2476056494@qq.com
 *
 */
public class BBFixedThreadPool {

	public static void main(String[] args) {
		//核心线程数量
		int corePoolSize = 2;
		//最大线程数量
		int maximumPoolSize = 2;
		//(maximumPoolSize-corePoolSize) 线程的空间时间
		long keepAliveTime = 0L;
		//keepAliveTime 单位
		TimeUnit unit = TimeUnit.SECONDS;
		ThreadFactory handler = Executors.defaultThreadFactory();
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(2);
		// 【1】获取线程池，固定2个线程
		ExecutorService fixedThreadPool = new ThreadPoolExecutor(corePoolSize , maximumPoolSize , keepAliveTime , unit ,
				workQueue, handler);
		// 【2】提交4个任务，观察任务的执行时间和线程name
		for (int i = 0; i < 4; i++) {
			Task task = new Task();
			fixedThreadPool.submit(task);
		}
	}

	public static class Task implements Runnable {
		@Override
		public void run() {
			// 执行2秒的逻辑
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < 2000) {
				continue;
			}
			System.out.println(System.currentTimeMillis() + Thread.currentThread().getName());
		}
	}
}
