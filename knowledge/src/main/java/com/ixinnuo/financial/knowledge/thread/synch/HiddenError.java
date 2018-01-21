package com.ixinnuo.financial.knowledge.thread.synch;

import java.util.ArrayList;

/**
 * 没有异常，没有日志,隐藏的错误
 * @author 2476056494@qq.com
 *
 */
public class HiddenError {
	
	static ArrayList<Integer> list = new ArrayList<Integer>();

	public static void main(String[] args) throws Exception {
		System.out.println("========溢出问题==============");
		errorOverflow();
		System.out.println("========ArrayList问题:越界和数据减少==============");
		Thread t1 = new Thread(new ErrorList());
		Thread t2 = new Thread(new ErrorList());
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(list.size());
		
		
	}
	
	/**
	 * 溢出问题，超过了最大值
	 */
	public static void errorOverflow(){
		//int最大值21亿多，Integer.MAX_VALUE =2147483647
		int a = 1047483647;
		int b = 1447483647;
		int avg = (a+b)/2;
		System.out.println("平均数：" + avg);
	}
	
	/**
	 * ArrayList线程不安全，可以替换为vector</br>
	 * 1.扩容时，内部一致性被破坏，被其他线程读到不一致内容，出现越界问题</br>
	 * 2.多个线程对同一个位置赋值，导致总数据量减少</br>
	 * 同样的HashMap在多线程下也会出现，内部一致性破坏，链表有可能成为闭环，两个元素的next分别指向对方，死循环，使用concurrentHashMap即可
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ErrorList implements Runnable{
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				list.add(i);
			}
		}
	}
	
	
}
