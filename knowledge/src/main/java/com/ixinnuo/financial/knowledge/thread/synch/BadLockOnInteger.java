package com.ixinnuo.financial.knowledge.thread.synch;

/**
 * 给integer对象加锁是不安全的，因为integer是不变对象，每次改变值都是新new一个integer，赋值给引用
 * 
 * @author carl
 *
 */
public class BadLockOnInteger {

	public static Integer i = 0;

	public static class AddCount implements Runnable {
		@Override
		public void run() {

			for (int j = 0; j < 10000; j++) {
				//【1】这里同步i是不安全的，应该同步i所属的类BadLockOnInteger.class
				synchronized (i) {
					i++;
				}
			}

		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new AddCount());
		Thread t2 = new Thread(new AddCount());
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(i);
		
	}

}
