package com.ixinnuo.financial.knowledge.datasort;

/**
 * 选择排序 在要排序的一组数中，选出最小的一个数与第一个位置的数交换； <br>
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。<br>
 * 时间复杂度n(n-1)/2=O(n^2)<br>
 * 选择排序和插入排序的区别
 * 选择排序是在未排序当中找到最小的，追加到已排序后面
 * 插入排序是在已排序当中找到比未排序小的数，插入到后面
 * 选择排序和插入排序的相同点
 * 都是把第一个元素当成已排序的
 * @author 2476056494@qq.com
 *
 */
public class SortAFSelect {

	public static void selectSort(int a[]) {

		int position;
		for (int i = 0; i < a.length; i++) {
			// 记录较小值的索引，每一趟都从i开始
			position = i;
			//从余下元素找到一个最小值
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[position]) {
					position = j;
				}
			}
			// 找到最小值，如果不是位置i，则交换
			if (position != i) {
				swap(a, i, position);
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
	public static void swap(int[] a, int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}

	public static void main(String[] args) {
		int a[] = { 1, 54, 6, 3, 78, 34, 12, 45 };
		selectSort(a);
		for (int i = 0; i < a.length; i++)
			System.out.printf("%d ", a[i]);
	}
}
