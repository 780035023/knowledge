package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import net.bytebuddy.utility.RandomString;

/**
 * LinkedBlockingQueue实现了Queue，增加了重入锁ReentrantLock，首尾分别各用一把锁，</br>
 * 增加了put，队尾增加元素，队列满的时候阻塞；</br>
 * take，队首获取元素并移除，队列空时阻塞
 * 
 * @author 2476056494@qq.com
 *
 */
public class DBLinkedBlockingQueue {

	// 意见箱
	volatile static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
	static int count = 30;
	static CountDownLatch latch = new CountDownLatch(count);

	public static void main(String[] args) {
		// 最大处理能力10的线程池，可改变生产量的大小来观察超过最大能力后的拒绝情况
		ExecutorService threadPool = BBNewThreadPool.newThreadPool(20);
		for (int i = 0; i < count; i++) {
			// 使用线程池提交任务的方式，这里使用计数器保证生产者先执行完毕
			threadPool.execute(new ProducerTask());
		}
		try {
			//阻塞，直到生产这全部执行完毕
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (!blockingQueue.isEmpty()) {
			// 消费者可以多线程
			threadPool.execute(new consumerTask());
		}
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}

		System.out.println("意见箱处理完毕");
	}

	public static class ProducerTask implements Runnable {

		@Override
		public void run() {
			String make = RandomString.make(5);
			try {
				blockingQueue.put(make);
				latch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("提意见" + make);
		}
	}

	public static class consumerTask implements Runnable {
		@Override
		public void run() {
			if (!blockingQueue.isEmpty()) {
				try {
					System.out.println(blockingQueue.take() + "意见已处理");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
