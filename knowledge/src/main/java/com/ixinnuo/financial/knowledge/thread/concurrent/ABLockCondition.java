package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
			lock.lock();
			try {
				//等待一个信号
				condition.await();
				//模拟业务逻辑处理，花费2秒
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		MyThread myThread = new MyThread(lock,condition);
		Thread t1 = new Thread(myThread);
		t1.start();
		lock.lock();
		try {
			//模拟业务逻辑，线程执行5秒
			TimeUnit.SECONDS.sleep(5);
			//通知其他等待条件的线程执行
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
