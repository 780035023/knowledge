package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * 选择一个基准元素,通常选择第一个元素或者最后一个元素,<br>
 * 通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,<br>
 * 此时基准元素在其排好序后的中间位置
 * 
 * @author 2476056494@qq.com
 *
 */
public class SortAGQuick {

	public static void main(String[] args) {
		SortAGQuick sort = new SortAGQuick();
		Integer a[] = { 49, 64, 5, 4, 62, 54, 23, 34, 15, 53, 51 };
		System.out.println("排序前数据" + Arrays.deepToString(a));
		sort.quick(a);
	}

	/**
	 * 选取枢纽元 以第一个元素为中轴排序，排序后，第一个元素移动到中间，并返回中间索引
	 * 
	 * @param list
	 * @param low
	 * @param high
	 * @return
	 */
	public int getMiddle(Integer[] list, int low, int high) {
		int tmp = list[low]; // 数组的第一个作为中轴
		while (low < high) {
			// 从右侧开始寻找比中轴小的
			while (low < high && list[high] >= tmp) {
				high--;
			}
			list[low] = list[high]; // 比中轴小的记录移到低端
			// 从左侧开始寻找比中轴大的
			while (low < high && list[low] <= tmp) {
				low++;
			}
			list[high] = list[low]; // 比中轴大的记录移到高端
		}
		list[low] = tmp; // 中轴记录到尾
		System.out.println(tmp + "中轴排序后" + Arrays.deepToString(list));
		return low; // 返回中轴的位置
	}

	public void _quickSort(Integer[] list, int low, int high) {
		if (low < high) {
			//
			int middle = getMiddle(list, low, high); // 将list数组进行一分为二
			_quickSort(list, low, middle - 1); // 对低字表进行递归排序
			_quickSort(list, middle + 1, high); // 对高字表进行递归排序
		}
	}

	public void quick(Integer[] a2) {
		// 少于1个的不需要排序
		if (a2.length > 1) { // 查看数组是否为空
			_quickSort(a2, 0, a2.length - 1);
		}
	}
}
