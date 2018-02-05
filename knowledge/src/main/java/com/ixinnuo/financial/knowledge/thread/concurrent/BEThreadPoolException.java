package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BEThreadPoolException {

	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		for (int i = 0; i < 5; i++) {
			Divide divide = new Divide(100, i);
			//submit会吃掉异常信息，改为execute,或使用
			//【1】直接submit会吃掉异常信息
			//threadPoolExecutor.submit(divide);
			Future<?> future = threadPoolExecutor.submit(divide);
			try {
				//【2】加上future.get()可获取异常信息
				future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			//【3】或者直接使用execute
			//threadPoolExecutor.execute(divide);
		}
	}

	/**
	 * 负责除法的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class Divide implements Runnable {
		int beDivided, divisor;

		public Divide(int beDivided, int divisor) {
			this.beDivided = beDivided;
			this.divisor = divisor;
		}

		@Override
		public void run() {
			int result = beDivided / divisor;
			System.out.println(result);
		}
	}
}
