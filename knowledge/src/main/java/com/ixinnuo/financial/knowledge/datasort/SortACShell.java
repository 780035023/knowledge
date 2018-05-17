package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * 希尔排序，插入排序的加强版，也叫缩减增量排序<br>
 * 原数据，按照增量d1=n/2分成若干组，每组分别插入排序，<br>
 * 然后再用一个较小增量d2=d1/2分成若干组,每组分别插入排序，当增量递减到1则完成排序<br>
 * 比如[3, 44, 38, 5, 47, 15]<br>
 * 第一次按增量3=6/2排序，则是3和5一组，不变，44和47一组，不变，38和15一组，15插入38位置，排完之后<br>
 * 3排序后[3, 44, 15, 5, 47, 38]<br>
 * 第二次按增量1=3/2排序，则是整个一组，直接插入
 * 
 * @author 2476056494@qq.com
 *
 * @param <T>
 */
public class SortACShell<T extends Comparable<T>> {

	void shellsort(T[] a) {
		System.out.println("排序前" + Arrays.deepToString(a));
		for (int gap = a.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < a.length; i++) {
				// 记录下这个值，后面移动时，会被覆盖
				T tmp = a[i];
				int j;
				// 相差两个增量的数据进行比较
				for (j = i; j >= gap; j -= gap) {
					if (tmp.compareTo(a[j - gap]) < 0) {
						// 比前面的小，把前面的向后挪增量位,只移动前面元素不是交换
						a[j] = a[j - gap];
					} else {
						// 比前面的大，结束，再往前更小，不可能找到位置
						break;
					}
				}
				a[j] = tmp;
			}
			System.out.println("增量" + gap + "排序后" + Arrays.deepToString(a));
		}
	}

	public static void main(String[] args) {
		SortACShell<Integer> sort = new SortACShell<Integer>();
		Integer[] array = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
		// Integer[] array = { 3, 44, 38, 5, 47, 15 };
		sort.shellsort(array);
		System.out.println("排序后：" + Arrays.deepToString(array));
	}
}
