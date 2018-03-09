package com.ixinnuo.financial.knowledge.thread.mode;
/**
 * 最简单，最常用的单例模式，线程也安全，就是对象的初始化不确定
 * @author 2476056494@qq.com
 *
 */
public class AASingleton {
	//【1】单例类的加载，或者任何单例类的成员或方法的引用，都会导致类的初始化，并创建对象实例，因为下面的写法
	private static AASingleton instance = new AASingleton();
	
	public static int count = 0; 

	private AASingleton() {
		System.out.println("Singleton is created...");
	}
	
	public static AASingleton getInstance(){
		return instance;
	}
	
	public static void main(String[] args) {
		//【2】任何单例类的成员或方法的引用，都会导致类的初始化，并创建对象实例
		System.out.println(AASingleton.count);
		System.out.println(AASingleton.getInstance());
	}
}
