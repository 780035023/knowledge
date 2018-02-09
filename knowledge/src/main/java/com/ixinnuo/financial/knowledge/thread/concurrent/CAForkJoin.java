package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 自行拆分的任务，实现什么条件下拆分，拆分的结果回收
 * 
 * @author 2476056494@qq.com
 *
 */
public class CAForkJoin {

	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		// 【1】线程池，大小
		ForkJoinPool ForkJoinPool = new ForkJoinPool(4);
		CountTask CountTask = new CountTask(1, 1000000000);
		// 【2】提交任务
		ForkJoinTask<Long> submit = ForkJoinPool.submit(CountTask);
		try {
			System.out.println(submit.get());
			System.out.println("耗时：" + (System.currentTimeMillis() - currentTimeMillis));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class CountTask extends RecursiveTask<Long> {
		private static final long serialVersionUID = 1L;
		/**
		 * 阀门，可计算的深度
		 */
		private final static int threshold = 10;
		/**
		 * 拆分大小
		 */
		private final static int forkNum = 3;
		/* 开始结束,包前包后 */
		private long start, end;

		public CountTask(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		protected Long compute() {
			long sum = 0;
			boolean canCompute = end - start < threshold;
			// 【3】没有超过计算深度的直接计算
			if (canCompute) {
				for (long i = start; i <= end; i++) {
					sum = sum + i;
				}
				return sum;
				// 【4】超过深度的，拆分成指定分叉,并收集子任务的结果
			} else {
				// 拆分后的步长
				long step = (end - start) / forkNum;
				List<CountTask> countTaskList = new ArrayList<CountTask>();
				for (int i = 0; i < forkNum; i++) {
					CountTask countTask = new CountTask(start, start + step);
					start = start + step;
					countTask.fork();
					// 【5】 不要在这里直接join，会阻塞任务，先放到集合里面
					// Long result = countTask.join();
					// sum = sum + result;
					countTaskList.add(countTask);
				}
				//【6】获取任务的结果会阻塞
				for (CountTask countTask : countTaskList) {
					Long result = countTask.join();
					sum = sum + result;
				}
			}
			return sum;
		}

	}
}
