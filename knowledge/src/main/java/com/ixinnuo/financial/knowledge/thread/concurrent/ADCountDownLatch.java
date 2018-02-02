package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.CountDownLatch;
/**
 * 倒计数器
 * @author 2476056494@qq.com
 *
 */
public class ADCountDownLatch {

	public static void main(String[] args) {
		//【1】设置需要计数的数量，即多少个任务
		CountDownLatch countDownLatch = new CountDownLatch(2);
		// 【2】实例化两个任务
		CheckEngine checkEngine = new CheckEngine(countDownLatch);
		CheckTank checkTank = new CheckTank(countDownLatch);
		// 【3】启动线程，执行两个任务，每个任务执行完毕，内部一定要countdown一次
		Thread t1 = new Thread(checkEngine, "checkEngine");
		Thread t2 = new Thread(checkTank, "checkTank");
		t1.start();
		t2.start();
		try {
			//【4】阻塞，等待两个任务执行完毕再继续,等同于注释掉的两个join
			countDownLatch.await();
//			t1.join();
//			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 等待起飞
		System.out.println("起飞....");
	}

	/**
	 * 检查发动机
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class CheckEngine implements Runnable {
		private CountDownLatch countDownLatch;

		public CheckEngine(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			// 模拟业务逻辑，线程执行2秒
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < 5000) {
				continue;
			}
			System.out.println("发动机检查完毕...");
			countDownLatch.countDown();
		}
	}

	/**
	 * 检查油箱
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class CheckTank implements Runnable {
		private CountDownLatch countDownLatch;

		public CheckTank(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			// 模拟业务逻辑，线程执行2秒
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start < 2000) {
				continue;
			}
			System.out.println("油箱检查完毕...");
			countDownLatch.countDown();
		}
	}
}
