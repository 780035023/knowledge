package com.ixinnuo.financial.knowledge.thread.mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;

public class CAConcurrentSearch {

	// 待查找数组
	static int arr[] = new int[100];
	// 按长度拆分数组
	static int subArraySize = 10;
	// 要查找的数据
	static int searchValue = 123;
	// 线程池,最大10个任务
	static ExecutorService pool = BBNewThreadPool.newThreadPool(2);
	// 查找到的索引位置，-1表示不存在,
	static AtomicInteger result = new AtomicInteger(-1);
	// 存放任务返回的结果结果
	static List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

	public static void main(String[] args) throws Exception {
		// 初始化数组
		Random rd = new Random();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = rd.nextInt(100);
		}
		System.out.println(Arrays.toString(arr));
		// 划分数组，10个长度一组，避免余数和不足，多加1个
		int subArrayNum = arr.length / subArraySize + 1;
		int beginPos = 0;
		for (int i = 0; i < subArrayNum; i++) {
			int endPos = beginPos + subArraySize;
			if (endPos > arr.length) {
				endPos = arr.length;
			}
			// 提交任务,改变不同的查找值 观察
			searchValue = arr[35];
			Future<Integer> submit = pool.submit(new SearchTask(searchValue, beginPos, endPos));
			futures.add(submit);
			beginPos = endPos;
		}
		futures.forEach(item -> {
			try {
				// get是阻塞的，来保证任务执行完成
				System.out.println(item.get());
			} catch (Exception e) {
			}
		});
		pool.shutdownNow();
		System.out.println("最终结果：" + result.get());
	}

	/**
	 * 线程实现查找的任务
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	static class SearchTask implements Callable<Integer> {
		// 定义要查找的值，起始位置
		int searchValue, beginPos, endPos;

		public SearchTask(int searchValue, int beginPos, int endPos) {
			this.searchValue = searchValue;
			this.beginPos = beginPos;
			this.endPos = endPos;
		}

		@Override
		public Integer call() throws Exception {
			for (int i = beginPos; i < endPos; i++) {
				// 如果已经找到，结束
				if (result.get() >= 0) {
					return result.get();
				}
				// 如果能匹配
				if (arr[i] == searchValue) {
					// 更新result记录
					if (result.compareAndSet(-1, i)) {
						return i;
					}
					// 更新失败表示已经找到被改过
					return result.get();
				}
			}
			// 该范围内没有找到
			return -1;
		}
	}

}
