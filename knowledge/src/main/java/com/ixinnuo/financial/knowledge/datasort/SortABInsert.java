package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * 插入排序，
 * <p>
 * 利用一个事实：在已经排序好的数据中插入一个数
 * <p>
 * 不稳定，在已经排序好的情况下较快，一次只能移动一个
 * https://visualgo.net/zh/sorting
 * 选择排序和插入排序的区别
 * 选择排序是在未排序当中找到最小的，追加到已排序后面
 * 插入排序是在已排序当中找到比未排序小的数，插入到这个值后面，同时后面的已排序数据向后平移
 * 选择排序和插入排序的相同点
 * 都是把第一个元素当成已排序的
 * 
 * @author 2476056494@qq.com
 *
 */
public class SortABInsert<T extends Comparable<T>> {

	/**
	 * 将第一个元素标记为已排序,每个元素 依次从它所在的位置向前寻找合适的位置，插入
	 * 
	 * @param array
	 */
	void insertSort(T[] array) {
		// 从第二个开始未排序的
		for (int i = 1; i < array.length; i++) {
			//记录下这个值，后面移动时，会被覆盖
			T tmp = array[i];
			int j;
			for (j = i; j > 0; j--) {
				if (tmp.compareTo(array[j-1]) < 0) {
					// 比前面的小，把前面的向后挪一位,只移动前面元素不是交换
					array[j] = array[j - 1];
				} else {
					// 比前面的大，结束，再往前更小，不可能找到位置
					break;
				}
			}
			// 把当前值赋给，赋给比较后的j位置
			array[j] = tmp;

		}
	}

	public static void main(String[] args) {
		SortABInsert<Integer> sort = new SortABInsert<Integer>();
		Integer[] array = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
		sort.insertSort(array);
		System.out.println(Arrays.deepToString(array));
	}
}
