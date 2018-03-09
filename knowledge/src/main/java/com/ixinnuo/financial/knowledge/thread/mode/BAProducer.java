package com.ixinnuo.financial.knowledge.thread.mode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者
 * 
 * @author 2476056494@qq.com
 *
 */
public class BAProducer implements Runnable {
	// 总数原子操作
	public static AtomicInteger count = new AtomicInteger();

	//private BlockingQueue<BAPCData> queue;
	private BlockingQueue<BAPCData> queue;

	public BAProducer(BlockingQueue<BAPCData> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			// 放入数据
			int incrementAndGet = count.incrementAndGet();
			boolean offer = queue.offer(new BAPCData(incrementAndGet), 2, TimeUnit.SECONDS);
			System.out.println(Thread.currentThread().getName() + incrementAndGet + "放入队列" + (offer ? "成功" : "失败"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
