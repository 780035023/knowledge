package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池,
 * LinkedBlockingQueue 无界阻塞队列，使用的节点，node实现
 * ArrayBlockingQueue  有界阻塞队列，使用的数组，所以必须定长
 * 
 * @author 2476056494@qq.com
 *
 */
public class BBNewThreadPool {

	public static void main(String[] args) {
		//核心线程数量
		int corePoolSize = 2;
		//最大线程数量
		int maximumPoolSize = 2;
		//(maximumPoolSize-corePoolSize) 线程的空间时间
		long keepAliveTime = 0L;
		//keepAliveTime 单位
		TimeUnit unit = TimeUnit.SECONDS;
		//默认的线程工厂，创建线程
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		//阻塞队列
		//BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(2);
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(2);
		//超过线程池的最大处理能力时，拒绝策略
		RejectedExecutionHandler rejectedExeutionHandler = new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				System.out.println(System.currentTimeMillis() + executor.toString() + r.toString() + "..rejected...");
				
			}
		};
		// 【1】获取线程池，固定2个线程
		ExecutorService executorService = new ThreadPoolExecutor(corePoolSize , maximumPoolSize , keepAliveTime , unit ,
				workQueue, threadFactory,rejectedExeutionHandler );
		// 【2】提交40个任务，观察任务的执行时间和线程name
		for (int i = 0; i < 40; i++) {
			Task task = new Task();
			executorService.submit(task);
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
