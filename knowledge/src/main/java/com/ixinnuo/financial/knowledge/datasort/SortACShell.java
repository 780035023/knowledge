package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * 希尔排序，插入排序的加强版，也叫缩减增量排序<br>
 * 原数据，按照增量看成一组，每组分别插入排序，增量递减到1则完成排序<br>
 * 比如[3, 44, 38, 5, 47, 15]<br>
 * 第一次按增量3排序，则是3和5比较不变，44和47比较不变，38和15比较，15插入38位置，排完之后<br>
 * 3排序后[3, 44, 15, 5, 47, 38]<br>
 * 
 * @author 2476056494@qq.com
 *
 * @param <T>
 */
public class SortACShell<T extends Comparable<T>> {

	void shellsort(T[] a) {
		System.out.println("排序前" + Arrays.deepToString(a));
		int j;
		for (int gap = a.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < a.length; i++) {
				T tmp = a[i];
				// 相差两个增量的数据进行比较
				for (j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap) {
					a[j] = a[j - gap];
				}
				a[j] = tmp;
			}
			System.out.println(gap + "排序后" + Arrays.deepToString(a));
		}
	}

	public static void main(String[] args) {
		SortACShell<Integer> sort = new SortACShell<Integer>();
		// Integer[] array = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19,
		// 50, 48 };
		Integer[] array = { 3, 44, 38, 5, 47, 15 };
		sort.shellsort(array);
		System.out.println(Arrays.deepToString(array));
	}
}
