package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * https://visualgo.net/zh/sorting 冒泡
 * 交换相邻两个元素，把较大的向后移动
 * @author 2476056494@qq.com
 *
 */
public class SortAABubble {

	public static void main(String[] args) {
		bubbleSort();
		
	}

	public static void bubbleSort() {
		Integer[] a = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
		int temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		System.out.println(Arrays.deepToString(a));
	}
}
