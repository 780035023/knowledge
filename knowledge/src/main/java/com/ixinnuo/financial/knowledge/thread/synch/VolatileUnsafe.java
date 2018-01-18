package com.ixinnuo.financial.knowledge.thread.synch;

/**
 * volatile 线程不安全的，只是保证修改的动作被其他线程看见 当多个线程同时修改的时候仍然有冲突
 * 两个线程分别对count加10000，结果并不一定是20000
 * 
 * @author 2476056494@qq.com
 *
 */
public class VolatileUnsafe {

	static volatile int count;

	public static void main(String[] args) throws Exception {
		Account account = new Account();
		// 下面的两个线程使用了Account的同一个对象实例
		Thread t1 = new Thread(account);
		Thread t2 = new Thread(account);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		// 最终的结果并不一定是20000
		System.out.println(count);
	}

	public static class Account implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				increase();
			}
		}

		public void increase() {
			VolatileUnsafe.count++;
		}
	}

}
