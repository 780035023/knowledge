package com.ixinnuo.financial.knowledge.thread.mode;

/**
 * 完美的单例模式，精准实例化对象，多线程效率高没有加锁
 * 
 * @author 2476056494@qq.com
 *
 */
public class ACSingleton {

	public static int count = 0;

	private ACSingleton() {
		System.out.println("Singleton is created...\n" + this);
	}

	// 【1】私有静态内部类 持有静态属性指向单例的构造方法
	private static class SingletonHolder {
		private static ACSingleton instance = new ACSingleton();

		private SingletonHolder() {
			System.out.println("SingletonHolder is created...\n" + this);
		}
	}

	// 【2】从内部类静态属性获取单例对象
	public static ACSingleton getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		// 【3】单例类的成员或方法的引用，不会导致类的初始化
		System.out.println(ACSingleton.count);
		// 【4】获取对象的方法，多线程环境下安全效率
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println(ACSingleton.getInstance());
				}
			}).start();
		}
	}
}
