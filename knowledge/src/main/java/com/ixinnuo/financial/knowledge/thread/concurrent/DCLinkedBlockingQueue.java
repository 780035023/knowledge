package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import net.bytebuddy.utility.RandomString;

/**
 * LinkedBlockingQueue实现了Queue，增加了重入锁ReentrantLock和条件Condition，首尾分别各用一把锁，Condition控制队列是否为空</br>
 * 增加了put，队尾增加元素，队列满的时候阻塞；</br>
 * take，队首获取元素并移除，队列空时阻塞 体验阻塞的情况
 * 这里分别用两个线程，一个处理队首消费 ，一个处理队尾生产
 * @author 2476056494@qq.com
 *
 */
public class DCLinkedBlockingQueue {
	static int capacity = 10;
	// 意见箱
	volatile static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>(capacity);

	public static void main(String[] args) {
		// 最大处理能力10的线程池，可改变生产量的大小来观察超过最大能力后的拒绝情况
		ExecutorService threadPool = BBNewThreadPool.newThreadPool(2);
		threadPool.execute(new ProducerTask());
		threadPool.execute(new consumerTask());
		// 消费者可以多线程
		threadPool.shutdown();
		while (!threadPool.isTerminated()) {
		}

		System.out.println("意见箱处理完毕");
	}

	/**
	 * 负责填充队列的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ProducerTask implements Runnable {

		@Override
		public void run() {
			String make = RandomString.make(5);
			try {
				for (int i = 0; i < 2 * capacity; i++) {
					make = RandomString.make(5);
					blockingQueue.put(make);
					System.out.println("提意见" + make);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 负载消费队列的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class consumerTask implements Runnable {
		@Override
		public void run() {
			try {
				while (true) {
					System.out.println(blockingQueue.take() + "意见已处理");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
