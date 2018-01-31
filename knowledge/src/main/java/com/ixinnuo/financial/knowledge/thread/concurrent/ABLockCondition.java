package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 重入锁条件的使用，注意依赖关系，前后顺序，否则容易阻塞，死锁
 * @author 2476056494@qq.com
 *
 */
public class ABLockCondition {

	public static class MyThread implements Runnable{
		private  ReentrantLock lock;
		private  Condition condition;
		
		public MyThread(ReentrantLock lock,Condition condition) {
			this.lock = lock;
			this.condition = condition;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "开始执行...");
			System.out.println(Thread.currentThread().getName() + "等待锁...");
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "获得锁...");
			try {
				System.out.println(Thread.currentThread().getName() + "等待条件...");
				//等待一个信号,自动释放锁，其他线程可获得锁
				condition.await();
				System.out.println(Thread.currentThread().getName() + "达到条件...");
				//模拟业务逻辑，线程执行2秒
				long start = System.currentTimeMillis();
				while(true){
					if(System.currentTimeMillis() - start > 2000){
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
				System.out.println(Thread.currentThread().getName() + "释放锁...");
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		//【1】实例化线程对象
		MyThread myThread = new MyThread(lock,condition);
		Thread t1 = new Thread(myThread,"myt-1");
		//【2】启动子线程
		t1.start();
		//模拟业务逻辑，线程执行5秒
		long start = System.currentTimeMillis();
		while(true){
			if(System.currentTimeMillis() - start > 5000){
				break;
			}
		}
		try {
			//【3】当前线程获取锁
			System.out.println(Thread.currentThread().getName() + "等待锁...");
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "获得锁...");
			//【4】通知其他等待条件的线程执行，必须先获得锁，必须在condition.await()之后执行，如果在之前执行，那么await的线程将永远阻塞
			condition.signal();
			System.out.println(Thread.currentThread().getName() + "通知其他线程执行...");
		}finally{
			//必须释放锁，否则其他线程就算达到执行条件也无法执行
			lock.unlock();
			System.out.println(Thread.currentThread().getName() + "释放锁...");
		}
		
	}
}
