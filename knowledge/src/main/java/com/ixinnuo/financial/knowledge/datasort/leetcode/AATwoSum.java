package com.ixinnuo.financial.knowledge.datasort.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AATwoSum {
	/**
	 * 给定一个整数数列没有负数，找出其中和为特定值的那两个数。<br>
	 * 
	 * 你可以假设每个输入都只会有一种答案，同样的元素不能被重用。 <br>
	 * <h3>实例</h3><br>
	 * 给定 nums = [2, 7, 11, 15], target = 9<br>
	 * 
	 * 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]<br>
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSumNoNeganive(int[] nums, int target) {
		if (nums.length < 2) {
			return null;
		}
		int[] result = new int[2];
		// 1.排除所有大于等于target的元素
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] <= target) {// 这里取等号，考虑0这种情况
				list.add(i);// 把位置放进集合
			}
		}
		// 2.两两相加比较，找到结果结束，假设只有一种结果
		for (int j = 0; j < list.size(); j++) {
			int index0 = list.get(j);
			for (int k = j + 1; k < list.size(); k++) {
				int index1 = list.get(k);
				if (nums[index0] + nums[index1] == target) {
					result[0] = index0;
					result[1] = index1;
					return result;
				}
			}

		}

		return result;
	}

	/**
	 * 常规解法，两两相加比较结果
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum2(int[] nums, int target) {
		if (nums.length < 2) {
			return null;
		}
		int[] result = new int[2];
		for (int j = 0; j < nums.length; j++) {
			for (int k = j + 1; k < nums.length; k++) {
				if (nums[j] + nums[k] == target) {
					result[0] = j;
					result[1] = k;
					return result;
				}
			}

		}

		return result;
	}

	/**
	 * target减去一个元素的值得到另一个元素应该的取值，判断余下的元素是否存在此值，只要比较即可，避免每一次都做加法运算
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		if (nums.length < 2) {
			return null;
		}
		int[] result = new int[2];
		for (int j = 0; j < nums.length; j++) {
			int sub = target - nums[j];
			for (int k = j + 1; k < nums.length; k++) {
				if (nums[k] == sub) {
					result[0] = j;
					result[1] = k;
					return result;
				}
			}

		}

		return result;
	}

	public static void main(String[] args) {
		AATwoSum sum = new AATwoSum();
		// int[] nums={2, 7, 11, 15};
		int[] nums = { 3, 2, 4 };
		int[] twoSum = sum.twoSum(nums, 6);
		System.out.println(twoSum[0] + "," + twoSum[1]);

	}
}
