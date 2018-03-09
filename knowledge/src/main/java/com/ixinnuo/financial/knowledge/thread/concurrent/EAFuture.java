package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EAFuture {

	public static void main(String[] args) throws Exception {
		ExecutorService newThreadPool = BBNewThreadPool.newThreadPool(20);
		List<Future<String>> futures = new ArrayList<Future<String>>();
		String name = "carl";
		for (int i = 0; i < 10; i++) {
			Future<String> submit = newThreadPool.submit(new RealData(name + i));
			futures.add(submit);
			// 模拟业务耗时1秒
			Thread.sleep(1000);
		}
		newThreadPool.shutdown();
		//遍历
		futures.forEach(item -> {
			try {
				// get是阻塞的，来保证任务执行完成
				String future = item.get();
				System.out.println(future);
			} catch (Exception e) {
			}
		});

	}

	static class RealData implements Callable<String> {
		private String name;

		public RealData(String name) {
			this.name = name;
		}

		@Override
		public String call() throws Exception {
			// System.out.println(Thread.currentThread().getName() + name);
			Thread.sleep(2000);// 模拟业务耗时2秒
			return "hello" + name;
		}
	}
}
