package com.ixinnuo.financial.knowledge.datasort;

import java.util.Arrays;

/**
 * 堆排序,堆排序是一种树形选择排序 当且仅当满足（hi>=h2i,hi>=2i+1） <br>
 * 调整它们的存储序，使之成为一个堆，这时堆的根节点的数最大。<br>
 * 然后将根节点与堆的最后一个节点交换。然后对前面(n-1)个数重新调整使之成为堆。依此类推，<br>
 * 直到只有两个节点的堆，并对它们作交换，最后得到有n个节点的有序序列<br>
 * 一是建立堆，二是堆顶与堆的最后一个元素交换位置<br>
 * <p>
 * 依次挑出最大的向后放
 * @author 2476056494@qq.com
 *
 */
public class SortADHeap {

	/**
	 * 交换
	 * 
	 * @param data
	 * @param i
	 * @param j
	 */
	private void swap(int[] data, int i, int j) {
		// TODO Auto-generated method stub
		int tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}

	public void heapSort(int[] a) {
		System.out.println("开始排序");
		int arrayLength = a.length;
		// 循环建堆
		for (int i = 0; i < arrayLength - 1; i++) {
			// 建堆

			buildMaxHeap(a, arrayLength - 1 - i);
			// 交换堆顶和最后一个元素
			swap(a, 0, arrayLength - 1 - i);
			System.out.println(Arrays.toString(a));
		}
	}

	// 对data数组从0到lastIndex建大顶堆
	private void buildMaxHeap(int[] data, int lastIndex) {
		// TODO Auto-generated method stub
		// 从lastIndex处节点（最后一个节点）的父节点开始
		for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
			// k保存正在判断的节点
			int k = i;
			// 如果当前k节点的子节点存在
			while (k * 2 + 1 <= lastIndex) {
				// k节点的左子节点的索引
				int biggerIndex = 2 * k + 1;
				// 如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
				if (biggerIndex < lastIndex) {
					// 若果右子节点的值较大
					if (data[biggerIndex] < data[biggerIndex + 1]) {
						// biggerIndex总是记录较大子节点的索引
						biggerIndex++;
					}
				}
				// 如果k节点的值小于其较大的子节点的值
				if (data[k] < data[biggerIndex]) {
					// 交换他们
					swap(data, k, biggerIndex);
					// 将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
					k = biggerIndex;
				} else {
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		SortADHeap sort = new SortADHeap();
		int[] data = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
		sort.buildMaxHeap(data, data.length - 1);
		sort.heapSort(data);
	}
}
