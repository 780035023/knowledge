package com.ixinnuo.financial.knowledge.datasort.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 素数又称质数<br>
 * 一个大于1的自然数，除了1和它自身外，不能被其他自然数整除的数叫做质数；否则称为合数。 如1，2，3，5，7
 * 
 * @author 2476056494@qq.com
 *
 */
public class ABSuShu {

	public static void main(String[] args) {
		int n = 100;
		System.out.println(getPrimeNumberToN(n));
	}

	/**
	 * 得到1到n之间的素数，存到一个ArrayList集合
	 */
	private static List<Integer> getPrimeNumberToN(int n) {
		List<Integer> result = new ArrayList<>();
		for (int i = 1; i < n + 1; i++) {
			if (numberIsPrime(i)) {
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * 判断一个数是不是素数：只能被1和本身整除
	 * <p>
	 * 说明：从2开始除，不需要到n，也就是循环条件是 < n 就可以，这之间只要被整除了，那么他就不是素数了
	 */
	private static boolean numberIsPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
