package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 重入锁与synchronized区别
 * 1.重入锁需要显示的加锁和释放,即由jdk实现；synchronized关键字，由jvm虚拟机实现
 * 2.重入锁支持公平锁，排队，synchronized是不公平锁
 * @author 2476056494@qq.com
 *
 */
public class AAReentrantLock {
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new MyRunnable(), "mythread01");
		Thread t2 = new Thread(new MyRunnable(), "mythread02");
		t1.start();
		t2.start();
		//t2.interrupt();
	}

	public static class MyRunnable implements Runnable{
		
		/**
		 * 【1】true公平锁，按先后顺序，false非公平锁，随机
		 */
		public static ReentrantLock lock = new ReentrantLock(true);
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "start...");
			try {
				//【2】尝试获得锁，如果被别的线程占用，则获取不到，直接返回false，所以在unlock时需要判断，如果没有获取到锁的情况下，unlock会报错#IllegalMonitorStateException
				lock.tryLock();
				//【3】获取支持中断的锁
				lock.lockInterruptibly();
				//【4】普通锁
				lock.lock();
				System.out.println(Thread.currentThread().getName() + "获得锁...");
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				int i = 1;
				//【5】释放锁，保证所有获取的都释放
				while(lock.isHeldByCurrentThread()){
					lock.unlock();
					System.out.println(Thread.currentThread().getName() + "释放锁"+i+"次...");
					i++;
				}
			}
			System.out.println(Thread.currentThread().getName() + "end...");
			
		}
	}
}
