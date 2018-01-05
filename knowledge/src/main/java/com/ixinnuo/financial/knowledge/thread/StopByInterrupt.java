package com.ixinnuo.financial.knowledge.thread;

import java.util.concurrent.TimeUnit;

/**
 * jdk安全的停止线程 {@link Thread#interrupt()}
 * 一般在循环体中，长连接场景中，使用线程中断
 * @see StopThreadSafe#stopme
 * @author 2476056494@qq.com
 *
 */
public class StopByInterrupt implements Runnable{

	@Override
	public void run() {
		while(true){
			if(Thread.currentThread().isInterrupted()){
				//【5】判断是否标记了中断位，保持中断位标记不变
				//Thread.interrupted() 判断是否标记了中断位,并清除中断位标记
				System.out.println(Thread.currentThread().getName() + "interrupted");
				break;
			}
			try {
				//【2】 线程mythread-1睡眠5秒，回到主线程
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				//【4】线程mythread-1中断，抛出InterruptedException，并清除中断位标记，所以要再设置一下中断位标记
				System.err.println(Thread.currentThread().getName() + "interrupted when sleep");
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public static void main(String[] args) {
		StopByInterrupt stopByInterrupt = new StopByInterrupt();
		Thread t = new Thread(stopByInterrupt);
		t.setName("mythread-1");
		t.start();
		try {
			//【1】 主线程睡眠2秒，转向执行线程mythread-1
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 【3】设置线程mythread-1 中断位标记
		t.interrupt();
	}
}
