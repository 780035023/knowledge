package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.math.RandomUtils;
/**
 * 读写分离锁，获取锁的方式，阻塞规则
 * @author 2476056494@qq.com
 *
 */
public class ACReadWriteLock {

	private static List<Integer> list = new ArrayList<Integer>();

	public static void main(String[] args) {
		ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
		Lock readLock = readWriteLock.readLock();
		Lock writeLock = readWriteLock.writeLock();
		System.out.println("============读写阻塞==================");
		for (int i = 0; i < 5; i++) {
			Thread writeThread = new Thread(new WriteThread(writeLock), "write" + i);
			Thread readThread = new Thread(new ReadThread(readLock), "read" + i);
			writeThread.start();
			readThread.start();
		}
		try {
			//等待上面执行完毕
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("============写写阻塞，读读非阻塞==================");
		list.clear();
		for (int i = 0; i < 5; i++) {
			Thread writeThread = new Thread(new WriteThread(writeLock), "write" + i);
			writeThread.start();
		}
		for (int i = 0; i < 5; i++) {
			Thread readThread = new Thread(new ReadThread(readLock), "read" + i);
			readThread.start();
		}

	}

	/**
	 * 负责读数据的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ReadThread implements Runnable {
		private Lock lock;

		public ReadThread(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				lock.lockInterruptibly();
				// 模拟业务逻辑，线程执行2秒
				long start = System.currentTimeMillis();
				while (System.currentTimeMillis() - start < 2000) {
					continue;
				}
				System.out.println(Thread.currentThread().getName() + "读取数据：" + Arrays.deepToString(list.toArray()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 负责写数据的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class WriteThread implements Runnable {

		private Lock lock;

		public WriteThread(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				lock.lockInterruptibly();
				// 模拟业务逻辑，线程执行2秒
				long start = System.currentTimeMillis();
				while (System.currentTimeMillis() - start < 2000) {
					continue;
				}
				int nextInt = RandomUtils.nextInt(100);
				list.add(nextInt);
				System.out.println(Thread.currentThread().getName() + "写入数据：" + nextInt);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
