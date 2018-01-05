package com.ixinnuo.financial.knowledge.thread;
/**
 * 对象的wait和notify控制线程的等待和唤醒
 * @author 2476056494@qq.com
 *
 */
public class WaitNotify {

	public static class WaitThread extends Thread{
		public Object obj;
		@Override
		public void run() {
			synchronized (obj) {
				try {
					System.out.println(Thread.currentThread().getName() + "稍后释放obj监控器....");
					obj.wait();
					System.out.println(Thread.currentThread().getName() + "重新获取obj监控器....");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class NotifyThread extends Thread{
		public Object obj;
		@Override
		public void run() {
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + "已使用完obj监控器，稍后唤醒一个等待obj监控器的线程....");
				//唤醒了一个等待obj监控器的线程，但要等当前同步块执行完毕，因为还持有obj监控器呢
				obj.notify();
				System.out.println(Thread.currentThread().getName() + "释放obj监控器....");
			}
		}
	}
	
	public static void main(String[] args) {
		Object obj = new Object();
		WaitThread waitThread = new WaitThread();
		waitThread.setName("waitThread");
		waitThread.obj = obj;
		NotifyThread notifyThread = new NotifyThread();
		notifyThread.setName("notifyThread");
		notifyThread.obj = obj;
		waitThread.start();
		notifyThread.start();
	}
	
}
