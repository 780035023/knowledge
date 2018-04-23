package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * （1）全排列组合的递归规律：<br>
 * 集合s的全排列组合 all(s)=n+all(s-n);其中n为已经取出的集合<br>
 * 以集合 s={1,2,3}为例,则s的全排列组合为all(s)={1}+all({2,3});其中n={1},s-n={2,3}<br>
 * 通过以上例子，我们可以知道上述算法可以用递归来解决。<br>
 * 我们取极端情况，如果集合s为空，那么说明不需要再进行递归。<br>
 * 全排列组合，如果集合有4个元素，则全排列组合的个数为 A(4,4)=4*3*2*1=24种，代码如下：<br>
 * <p>
 * 
 * （2）m个数据集合中选出n个数据（有序）<br>
 * m个数据集合中选出n个数据规律为：mAn({m},n)=t+mAn(集合{m-t},m-t的大小)<br>
 * 考虑极端的情况，如果集合m里只取一个元素，那么直接把这个元素取出来即可。<br>
 * 如何集合有4个元素，取出其中的两个有序元素个数为C(3,2)=3<br>
 * 
 * @author 2476056494@qq.com
 *
 */
public class ZAPailie {
	// 统计个数
	private static int listAll;
	private static int mAn;

	/**
	 * 全排列 all(s)=n+all(s-n);
	 * 
	 * @param prefix
	 *            已取出的元素
	 * @param candidate
	 *            剩余的元素
	 */
	public static void listAll(String prefix, List candidate) {

		if (candidate.isEmpty()) {
			System.out.println(prefix);// 直接打印，也可以存到外部集合
			listAll++;
		}
		for (int i = 0; i < candidate.size(); i++) {
			// 注意，这里是new一个新对象，转换成linkList,移除一个对象是在不影响原来队列的基础上的
			List candidate2 = new LinkedList(candidate);
			String prefix2 = prefix + candidate2.remove(i);// 用于保存排列组合生成的结果
			// 递归 all(s)=n+all(s-n);
			listAll(prefix2, candidate2);// 注意，这里temp和s1都是全新的集合和字符串，并不是一直对一个集合来进行操作

		}

	}

	/**
	 * m个数据集合中选出n个数据（有序） mAn({m},n)=t+mAn(集合{m-t},m-t的大小)
	 * 
	 * @param t
	 *            已取出来的元素
	 * @param lst
	 *            源数据
	 * @param n
	 *            要取出来的元素个数
	 */
	public static void mAn(String t, List lst, int n) {
		for (int i = 0; i < lst.size(); i++) {
			// System.out.println("i="+i);
			List list2 = new LinkedList(lst);
			String t2 = t + (String) list2.remove(i);// 取出来的数字
			if (n == 1) {
				System.out.println(t2);// 以最极端 m个里面只取一个，直接把取出来的结果输出即可
				mAn++;
			} else {
				int n2 = n - 1;// 在同一层中n总量不能减,新加变量
				// mAn({m},n)=t+mAn(集合{m-t},m-t的大小)
				mAn(t2, list2, n2);
			}
		}

	}

	public static void main(String[] args) {
		String[] arr = { "1", "2", "3" };
		ZAPailie f = new ZAPailie();
		List<String> asList = Arrays.asList(arr);
		f.listAll("", asList);
		System.out.println("所有的排列个数：" + listAll);

		mAn("", asList, 2);
		System.out.println("所有的组合个数：" + mAn);
	}
}
