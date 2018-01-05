package com.ixinnuo.financial.knowledge.thread;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * join 等待依赖的线程执行完毕，也可设置等待时长，本质是wait依赖的线程，执行完毕后会notifyall</br>
 * yield thread静态方法，让出线程的执行时间,并一定退出执行，可能马上又抢到了cpu,使用不大
 * 
 * @author 2476056494@qq.com
 *
 */
public class JoinYield {

	public static class MythreadOne extends Thread {
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + "start");
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName() + "end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static class MythreadTwo extends Thread {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "start");
			System.out.println(Thread.currentThread().getName() + "end");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "start");
		MythreadOne t1 = new MythreadOne();
		t1.start();
		// 开关下面join可以观察执行效果
		t1.join();
		System.out.println(Thread.currentThread().getName() + "end");
		// 让出
		MythreadOne t2 = new MythreadOne();
		t2.start();
		Thread.yield();
		System.out.println(Thread.currentThread().getName() + "end2");
		
	}
}
