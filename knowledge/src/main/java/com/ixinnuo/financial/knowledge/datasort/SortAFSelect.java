package com.ixinnuo.financial.knowledge.datasort;

/**
 * 选择排序 在要排序的一组数中，选出最小的一个数与第一个位置的数交换； <br>
 * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
 * 
 * @author 2476056494@qq.com
 *
 */
public class SortAFSelect {

	public void selectSort() {
		int a[] = { 1, 54, 6, 3, 78, 34, 12, 45 };
		int position = 0;
		for (int i = 0; i < a.length; i++) {

			int j = i + 1;
			//记录较小值的索引
			position = i;
			//记录较小值
			int temp = a[i];
			for (; j < a.length; j++) {
				if (a[j] < temp) {
					temp = a[j];
					position = j;
				}
			}
			//找到最小值，交换到前面
			a[position] = a[i];
			a[i] = temp;
		}
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
	}
}
