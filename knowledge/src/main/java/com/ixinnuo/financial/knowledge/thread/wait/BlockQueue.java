package com.ixinnuo.financial.knowledge.thread.wait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockQueue {

	Logger log = LoggerFactory.getLogger(this.getClass());
	Object lock = new Object();

	private ArrayList<String> list = new ArrayList<>();

	private int size;

	// 初始化队列大小
	public BlockQueue(int size) {
		this.size = size;
		log.info("{}初始化队列大小{}完成", Thread.currentThread().getName(), size);
	}

	/**
	 * 存数据
	 * 
	 * @param element
	 */
	public void put(String element) {
		// 对象锁
		synchronized (lock) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (list.size() >= size) {
				log.info("{}队里已经满了，需要等待。。。。", Thread.currentThread().getName());
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 重新获得锁之后（只有在队列有空位置时才被notify,只有取数据的线程能执行，存数据的线程无法获取锁，进不来），从这里继续执行
			list.add(element);
			log.info("{}把数据{}放入了队列", Thread.currentThread().getName(), element);
			// 通知其他线程取数据
			lock.notifyAll();
		}
	}

	/**
	 * 取数据
	 * 
	 * @return
	 */
	public String get() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// 对象锁
		synchronized (lock) {
			if (list.size() == 0) {
				log.info("{}队里已经空了，需要等待。。。。", Thread.currentThread().getName());
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// 取出元素，需要移除
			String element = list.remove(0);
			log.info("{}把数据{}从队列中取出", Thread.currentThread().getName(), element);
			// 通知其他线程存数据
			lock.notifyAll();
			return element;

		}
	}

	public static void main(String[] args) {
		BlockQueue queue = new BlockQueue(7);
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					queue.put("a" + i);
				}
			}
		}, "T1").start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {
					queue.get();
				}
			}
		}, "T2").start();
	}
}
