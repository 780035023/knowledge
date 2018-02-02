package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 固定线程池,jdk自带的线程池不可用，不是线程无限扩张，就是队列无限扩张
 * @author 2476056494@qq.com
 *
 */
public class BAFixedThreadPool {

	public static void main(String[] args) {
		//【1】获取线程池，固定2个线程
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
		//【2】提交4个任务，观察任务的执行时间和线程name
		for (int i = 0; i < 4; i++) {
			Task task = new Task();
			fixedThreadPool.submit(task);
		}
	}

	public static class Task implements Runnable {
		@Override
		public void run() {
			//执行2秒的逻辑
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < 2000) {
				continue;
			}
			System.out.println(System.currentTimeMillis() + Thread.currentThread().getName());
		}
	}
}
