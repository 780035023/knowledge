package com.ixinnuo.financial.knowledge.thread.mode;
/**
 * 延迟，精准创建对象实例，但是同步导致多线程下效率低
 * @author 2476056494@qq.com
 *
 */
public class ABSingleton {
	//【1】任何单例类的成员或方法的引用，都会导致类的初始化，只有调用指定方法才创建对象实例
	private static ABSingleton instance = null;
	
	public static int count = 0; 

	private ABSingleton() {
		System.out.println("Singleton is created...\n" + this);
	}
	
	//【3】获取对象的方法，多线程环境下，必须加锁
	public static synchronized ABSingleton getInstance(){
		if(instance == null){
			instance = new ABSingleton();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		//【2】任何单例类的成员或方法的引用，都会导致类的初始化，只有调用指定方法才创建对象实例
		System.out.println(ABSingleton.count);
		//【3】获取对象的方法，多线程环境下，不加锁，可能获取到多个对象
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println(ABSingleton.getInstance());
				}
			}).start();
		}
	}
	
	
}
