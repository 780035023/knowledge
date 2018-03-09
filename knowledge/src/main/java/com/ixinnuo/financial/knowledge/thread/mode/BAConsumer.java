package com.ixinnuo.financial.knowledge.thread.mode;

import java.util.concurrent.BlockingQueue;

public class BAConsumer implements Runnable {


	private BlockingQueue<BAPCData> queue;

	public BAConsumer(BlockingQueue<BAPCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				BAPCData data = queue.take();
				int intData = data.getIntData();
				System.out.println(Thread.currentThread().getName() + "执行任务" + intData);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
