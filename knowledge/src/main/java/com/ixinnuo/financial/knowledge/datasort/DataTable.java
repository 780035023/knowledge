package com.ixinnuo.financial.knowledge.datasort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 抽象数据类型（abstract data type） ADT 表结构:<br>
 * 数组，连续存储，遍历方便；插入，删除开销大（要移动其他元素的位置，除非在末尾操作）<br>
 * 链表，分散的节点组成，每个节点含有元素和指向下一个节点的链，方便增删，不用其他元素<br>
 * 双向链表，节点含有元素和分别指向前一个后一个元素的链<br><hr>
 * ArrayList是可增长数组实现，get和set效率高，add和remove效率低，除非在末尾<br>
 * linkedList是双向链表，add和remove效率高，不容索引，get效率低
 * <br><hr>
 * 桟 是一种表结构，插入和删除只能在表的末端（栈顶）进行<br>
 * LinkedList的addLast和removeLast就是对桟的支持<br>
 * 应用：计算器的后缀表达式，数入栈，符号出桟两个数运算，结果入栈<br>
 *  <br><hr>
 * 队列 是一种表结构，插入和删除分别在两端（队尾和队首）进行<br>
 * LinkedList的addLast和removeLast就是对桟的支持<br>
 * @author 2476056494@qq.com
 *
 */
public class DataTable {

	static Integer[] arr = { 3, 44, 38, 5, 47, 15 };
	

	public static void main(String[] args) {
		try {
			//【1】返回的是java.util.Arrays.ArrayList<E>，不能增删操作
			List<Integer> asList = Arrays.asList(arr);
			//观察UnsupportedOperationException异常
			Iterator<Integer> iterator = asList.iterator();
			iteratorWithSub(asList,50);
			//
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(20);
		list.add(30);
		//【2】观察删除后的列表，两种操作的区别
		//iteratorWithSub(list,30);
		forWithSub(list,15);
		
		//
	}

	/**
	 * 遍历，并删除小于sub的值，在遍历过程中增删元素，迭代器才是正确的用法
	 * @param list
	 * @param sub
	 */
	static void iteratorWithSub(List<Integer> list,int sub) {
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer next = iterator.next();
			if (next.compareTo(sub) < 0) {
				iterator.remove();
				continue;
			}
			System.out.println(next);

		}
	}
	/**
	 * 遍历，并删除小于sub的值,错误的用法，for遍历时增删会影响list其他元素位置
	 * @param list
	 * @param sub
	 */
	static void forWithSub(List<Integer> list,int sub) {
		for (int i = 0; i < list.size(); i++) {
			Integer integer = list.get(i);
			if (integer.compareTo(sub) < 0) {
				list.remove(i);
				continue;
			}
			System.out.println(integer);
		}
	}

}
