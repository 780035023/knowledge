package com.ixinnuo.financial.knowledge.thread.synch;

/**
 * 通过synchronized保证线程安全 
 * 两个线程分别对count加10000
 * 
 * @author 2476056494@qq.com
 *
 */
public class SynchRight {

	static int count;

	public static void main(String[] args) throws Exception {
		Account account = new Account();
		// 下面的两个线程使用了Account的同一个对象实例
		Thread t1 = new Thread(account);
		Thread t2 = new Thread(account);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		// 最终的结果一定是20000
		System.out.println(count);
		System.out.println("===============分割线====================");
		// 清空
		count = 0;
		// 【2】使用的是不同的Account对象实例，对象锁失去作用
		Thread t3 = new Thread(new Account());
		Thread t4 = new Thread(new Account());
		t3.start();
		t4.start();
		t3.join();
		t4.join();
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

		// 【1】 作用于实例方法，相当于对当前实例加锁，等同于synchronized(this){}
		// 必须保证线程使用的是同一个account实例，否则无效，如【2】
		// 也可以把下面的方法变为static成为类方法，那么【2】就无所谓
		public synchronized void increase() {
			SynchRight.count++;
		}
	}
}
