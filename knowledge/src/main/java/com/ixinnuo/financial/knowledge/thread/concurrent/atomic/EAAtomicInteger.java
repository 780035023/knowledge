package com.ixinnuo.financial.knowledge.thread.concurrent.atomic;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;

/**
 * 原子级操作，cas无锁 使用volatile和cas保证线程安全
 * 
 * @author 2476056494@qq.com
 *
 */
public class EAAtomicInteger {

	static AtomicInteger count = new AtomicInteger();

	volatile static int count2 = 0;

	/**
	 * 每个任务递增10000
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	static class AddTask implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				// 1.AtomicInteger操作
				count.incrementAndGet();
				// 2.普通int类型
				count2++;
			}
		}
	}

	public static void main(String[] args) {
		ExecutorService pool = BBNewThreadPool.newThreadPool(50);
		long epochMilli = Instant.now().toEpochMilli();
		for (int i = 0; i < 250; i++) {
			pool.execute(new AddTask());
		}
		pool.shutdown();
		while (!pool.isTerminated()) {
			// 等待线程池所有任务结束,阻塞
		}
		long time = Instant.now().toEpochMilli() - epochMilli;
		System.out.println(count.get() + "耗时" + time);
		System.out.println(count2 + "耗时" + time);
	}

}
