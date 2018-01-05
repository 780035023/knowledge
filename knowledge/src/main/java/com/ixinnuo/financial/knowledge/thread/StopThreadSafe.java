package com.ixinnuo.financial.knowledge.thread;

import java.util.concurrent.TimeUnit;
/**
 * 自定义安全的停止线程
 * 1.全局标记
 * 2.提供开关动作
 * 3.run执行前第一步就是判断
 * @author 2476056494@qq.com
 *
 */
public class StopThreadSafe implements Runnable{

	public Object obj;
	//标记
	volatile boolean stopme = false;
	
	
	public void stopMe(){
		stopme = true;
	}
	@Override
	public void run() {
		if(stopme){
			System.out.println(Thread.currentThread().getName() + "exit by stop me");
			return ;
		}
		synchronized (obj) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "执行完毕");
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Object obj = new Object();
		StopThreadSafe stopThreadSafe = new StopThreadSafe();
		stopThreadSafe.obj = obj;
		Thread t1 = new Thread(stopThreadSafe);
		t1.start();
		t1.join();//等待线程t1执行完毕
		stopThreadSafe.stopMe();
		Thread t2 = new Thread(stopThreadSafe);
		t2.start();
		
	}
}
