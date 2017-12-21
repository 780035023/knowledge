package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 统计单词频率，归并
 * 
 * @author 2476056494@qq.com
 *
 */
public class BigFileFrequency {

	/**
	 * 文件个数
	 */
	private static long fileNum;
	/**
	 * 文件组的开始结束位置集合
	 */
	private static Set<StartEndPair> startEndPairs = new HashSet<StartEndPair>();

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		int threadSize = 10;
		args = new String[] { "C:\\Users\\aisino\\Desktop\\temp\\word.txt" };
		File bigFile = new File(args[0]);
		String parentPath = bigFile.getParent();
		// 存放分割的小文件目录
		File smallFilePath = new File(parentPath + File.separator + "breakUp");
		File[] listFiles = smallFilePath.listFiles();
		fileNum = listFiles.length;
		// 根据线程数计算分割后的文件组
		long everySize = fileNum / threadSize;
		try {
			calculateStartEnd(0, everySize);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<Future<List<Entry<String, Integer>>>> futureList = new LinkedList<>();
		// 保证线程结束后再执行
		// 每一个小文件开启一个线程
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize + 1);
		// FIXME：文件分组后是11组，但是线程池只有10个线程，每个线程都使用了计数cyclicBarrier.awit，当10个线程占用之后
		// 第11个小文件组仍然提交给ExecutorService，但是没有可用线程了，而主线程又再等所有的线程执行完毕，死锁了，不可能执行完毕
		// 简单解决，就是线程池的数量多给一个【按线程切分有余数】
		for (StartEndPair pair : startEndPairs) {
			System.out.println("分配文件组：" + pair);
			File[] smallFiles = Arrays.copyOfRange(listFiles, (int) pair.start, (int) pair.end + 1);
			System.out.println(smallFiles.length);
			MapReduceTask mapReduceTask = new MapReduceTask(smallFiles, bigFile);
			Future<List<Entry<String, Integer>>> submit = executorService.submit(mapReduceTask);
			futureList.add(submit);
		}
		try {
			executorService.shutdown();
			//阻塞等待任务执行完毕
			executorService.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("文件总个数: " + listFiles.length);
		System.out.println("启用线程: " + threadSize);
		System.out.println("切分小文件数组为: " + startEndPairs.size());
		System.out.println("总耗时毫秒: " + (System.currentTimeMillis() - startTime));
		List<Entry<String, Integer>> topWords = new LinkedList<>();
		for (Future<List<Entry<String, Integer>>> future : futureList) {
			try {
				List<Entry<String, Integer>> tempList = future.get();
				topWords.addAll(tempList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 把每个线程返回的top100合并排序
		Collections.sort(topWords, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				int cmp = o1.getValue() - o2.getValue();
				// 默认是升序，改为-则降序
				return (cmp == 0 ? o1.getKey().compareToIgnoreCase(o2.getKey()) : -cmp);
			}

		});
		// 打印top100
		System.out.println("top100:");
		for (int i = 0; i < topWords.size() && i < 100; i++) {
			System.out.println(topWords.get(i).getKey() + ":" + topWords.get(i).getValue());
		}

	}

	/**
	 * 计算分割文件组的开始结束的位置
	 * 
	 * @param start
	 * @param size
	 * @throws IOException
	 */
	private static void calculateStartEnd(long start, long size) throws IOException {
		// 开始位置最大为fileLength-1
		if (start > fileNum - 1) {
			return;
		}
		StartEndPair pair = new StartEndPair();
		pair.start = start;
		long endPosition = start + size - 1;
		// 最后一个分组的结束位置，不能超过总长度
		if (endPosition >= fileNum - 1) {
			pair.end = fileNum - 1;
			startEndPairs.add(pair);
			return;
		}

		pair.end = endPosition;
		startEndPairs.add(pair);
		// 递归，开始是上个结束位置+1，
		calculateStartEnd(endPosition + 1, size);

	}
}
