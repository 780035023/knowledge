package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * https://visualgo.net/zh/sorting 冒泡 交换相邻两个元素，把较大的向后移动，时间复杂度为n(n-1)/2=O(n^2),稳定排序
 * 
 * @author 2476056494@qq.com
 *
 */
public class SortAABubble {
	private static int count = 0;

	public static void main(String[] args) {
		Integer[] a = { 3, 44, 38, 5, 47, 15, 36, 26,2,  27, 46, 4, 19, 50, 48 };
		bubbleSort(a);
		System.out.println(Arrays.deepToString(a));
		System.out.println("元素个数" + a.length + ";比较次数" + count);

	}

	/**
	 * 冒泡算法，两两比较，大的往后
	 * 
	 * @param a
	 */
	public static void bubbleSort(Integer[] a) {
		boolean exchange;
		for (int i = a.length - 1; i > 0; i--) {
			exchange = false;//本趟开始之前，交换标志为假
			for (int j = 0; j < i; j++) {
				count++;
				if (a[j] > a[j + 1]) {
					exchange = true;
					swap(a, j, j + 1);
				}
			}
			// 如果这一趟比较完，没有需要交换的，则直接结束
			if (!exchange) {
				return;
			}
		}
	}

	/**
	 * 交换两个位置的元素
	 * 
	 * @param a
	 * @param x
	 * @param y
	 */
	public static void swap(Integer[] a, int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}
}
