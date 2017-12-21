package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

/**
 * 统计单词频率
 * 
 * @author 2476056494@qq.com
 *
 */
public class MapReduceTask implements Callable<List<Entry<String, Integer>>> {

	/**
	 * 待处理的小组文件
	 */
	private File[] listFiles;

	/**
	 * 大文件所在位置，方便同目录操作
	 */
	private File bigFile;

	/**
	 * 统计文件组的前100个频率最高的单词
	 */
	private List<Entry<String, Integer>> top100 = new LinkedList<Entry<String, Integer>>();

	public MapReduceTask(File[] listFiles, File bigFile) {
		this.listFiles = listFiles;
		this.bigFile = bigFile;
	}

	@Override
	public List<Entry<String, Integer>> call() throws Exception {
		String parentPath = bigFile.getParent();
		BufferedReader reader = null;
		Map<String, Integer> map = null;
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			try {
				reader = new BufferedReader(new FileReader(file));
				map = new HashMap<String, Integer>();
				String word = reader.readLine();
				while (null != word) {
					if (map.containsKey(word)) {
						map.put(word, map.get(word).intValue() + 1);
						// continue; 开启检错模式
					} else {
						map.put(word, 1);
					}
					word = reader.readLine();
				}
				reader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 当前文件的统计map根据value排序
			List<Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
			Collections.sort(list, new Comparator<Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					int cmp = o1.getValue() - o2.getValue();
					// 默认是升序，改为-则降序
					return (cmp == 0 ? o1.getKey().compareToIgnoreCase(o2.getKey()) : -cmp);
				}

			});
			String name = file.getName();
			File mapReducePath = new File(parentPath + File.separator + "mapReduce");
			if (!mapReducePath.exists()) {
				mapReducePath.mkdir();
			}
			File mapReduceFile = new File(mapReducePath, name);
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(mapReduceFile));
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < list.size(); j++) {
					sb = new StringBuffer();
					Entry<String, Integer> entry = list.get(j);
					sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\r\n");
					// System.out.println(sb.toString());
					writer.write(sb.toString());
				}
				writer.close();
				if (list.size() > 100) {
					this.merge(list.subList(0, 100));
				} else {
					this.merge(list);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return top100;
	}

	/**
	 * 合并数据，并按频率从高到低排序
	 * 
	 * @param src
	 */
	private void merge(List<Entry<String, Integer>> src) {
		List<Entry<String, Integer>> tmp = new LinkedList<>();
		int leftPos = 0;
		// 左侧最后一位指针
		int leftEnd = top100.size() - 1;
		int rightPos = 0;
		// 最多取前100个
		int rightEnd = src.size() > 100 ? 99 : src.size() - 1;
		int tmpPos = 0;// 临时数组第一位指针

		/* 两侧都没有比较完 */
		while (leftPos <= leftEnd && rightPos <= rightEnd) {
			Entry<String, Integer> entryTop100 = top100.get(leftPos);
			Entry<String, Integer> entrySrc = src.get(rightPos);
			// 右侧单词频率高
			if (entryTop100.getValue() < entrySrc.getValue()) {
				tmp.add(entrySrc);
				rightPos++;
				// 左侧单词频率高
			} else if (entryTop100.getValue() > entrySrc.getValue()) {
				tmp.add(entryTop100);
				leftPos++;
				// 单词频率一样高，比较单词顺序
			} else {
				// 左侧单词靠前
				if (entryTop100.getKey().compareTo(entrySrc.getKey()) >= 0) {
					tmp.add(entryTop100);
					leftPos++;
					// 右侧单词靠前
				} else {
					tmp.add(entrySrc);
					rightPos++;
				}
			}
			tmpPos++;
		}
		// 如果tmp没有达到100，则左右至少有一个已经空了，把剩下不空的直接转移过来；如果tmp达到100了，左右剩下的都丢弃

		/* 左侧还有剩余 */
		if (leftPos <= leftEnd && tmp.size() < 100) {
			tmp.addAll(top100.subList(leftPos, leftEnd + 1));
		}

		/* 右侧还有剩余 */
		if (rightPos <= rightEnd && tmp.size() < 100) {
			tmp.addAll(src.subList(rightPos, rightEnd + 1));
		}

		// 把合并后的临时集合扶正
		if (tmp.size() > 100) {
			top100 = tmp.subList(0, 100);
		} else {
			top100 = tmp;
		}
	}

}
