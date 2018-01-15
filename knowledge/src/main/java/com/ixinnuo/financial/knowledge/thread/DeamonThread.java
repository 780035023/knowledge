package com.ixinnuo.financial.knowledge.thread;

import java.util.concurrent.TimeUnit;
/**
 * 守护线程，当所有的工作线程执行完毕后，自动结束
 * @author carl
 *
 */
public class DeamonThread {

	public static class DeamonT extends Thread{
		@Override
		public void run() {
			while (true) {
				System.out.println("i am alive");
				try {
					//暂停1秒，是为了执行其他线程
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		DeamonT deamonT = new DeamonT();
		//【1】设置为守护线程，必须在start之前设置
		//deamonT.setDaemon(true);
		deamonT.start();
		
		try {
			//【2】这里的主线程就是工作线程，10秒后执行完毕，守护线程自动结束，如果deamonT取消守护线程设置，则不会停止
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
