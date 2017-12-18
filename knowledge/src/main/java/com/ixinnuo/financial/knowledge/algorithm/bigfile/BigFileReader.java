package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BigFileReader {
	/**
	 * 文件长度
	 */
	private static long fileLength;
	/**
	 * 文件片的开始结束位置集合
	 */
	private static Set<StartEndPair> startEndPairs = new HashSet<StartEndPair>();
	/**
	 * 读取文件随机内容,用来切割文件
	 */
	private static RandomAccessFile rAccessFile;

	/**
	 * 统计行数
	 */
	public static AtomicLong counter = new AtomicLong(0);

	public BigFileReader() {
	}

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		// 只需要4个参数
		File file = new File("C:\\Users\\aisino\\Desktop\\temp\\word.txt");
		int threadSize = 1;
		int bufferSize = 1024 * 1024 * 2;
		String charset = "UTF-8";
		try {
			// 只读模式
			rAccessFile = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileLength = file.length();
		long everySize = fileLength / threadSize;
		try {
			calculateStartEnd(0, everySize);
			rAccessFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// 保证线程结束后再执行
		CyclicBarrier cyclicBarrier = new CyclicBarrier(startEndPairs.size(), new Runnable() {
			@Override
			public void run() {
				System.out.println("文件总大小: " + fileLength);
				System.out.println("启用线程: " + threadSize);
				System.out.println("切分小文件数为: " + startEndPairs.size());
				System.out.println("总耗时毫秒: " + (System.currentTimeMillis() - startTime));
				System.out.println("文件总行数: " + counter.get());
			}
		});
		// 每一个小文件开启一个线程
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		IHandle handle = new HandlePrint();
		for (StartEndPair pair : startEndPairs) {
			System.out.println("分配分片：" + pair);
			// 只读模式
			try {
				rAccessFile = new RandomAccessFile(file, "r");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			SliceReaderTask sliceReaderTask = new SliceReaderTask(pair, cyclicBarrier, bufferSize, charset, rAccessFile,
					handle);
			executorService.execute(sliceReaderTask);
		}
		// 不再接收任务，并等待任务结束
		executorService.shutdown();

	}

	/**
	 * 计算分割文件的开始结束的位置
	 * 
	 * @param start
	 *            开始位置
	 * @param size
	 *            文件内容长度
	 * @throws IOException
	 */
	private static void calculateStartEnd(long start, long size) throws IOException {
		// 开始位置最大为fileLength-1
		if (start > fileLength - 1) {
			return;
		}
		StartEndPair pair = new StartEndPair();
		pair.start = start;
		long endPosition = start + size - 1;
		// 最后一个文件片段的结束位置，不能超过总长度
		if (endPosition >= fileLength - 1) {
			pair.end = fileLength - 1;
			startEndPairs.add(pair);
			return;
		}

		rAccessFile.seek(endPosition);
		byte tmp = (byte) rAccessFile.read();
		// 确定结束位置在行末尾
		while (tmp != '\n' && tmp != '\r') {
			endPosition++;
			if (endPosition >= fileLength - 1) {
				endPosition = fileLength - 1;
				break;
			}
			rAccessFile.seek(endPosition);
			tmp = (byte) rAccessFile.read();
		}
		pair.end = endPosition;
		startEndPairs.add(pair);
		// 递归，开始是上个结束位置+1，
		calculateStartEnd(endPosition + 1, size);

	}
	
	

}