package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.RecursiveTask;

public class CAForkJoin {

	public static class CountTask extends RecursiveTask<Long> {
		/**
		 * 阀门，可计算的深度
		 */
		private final static int threshold = 10000;
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
			if (canCompute) {
				for (long i = start; i <= end; i++) {
					sum = sum + i;
				}
				return sum;
			}
			return null;
		}

	}
}
