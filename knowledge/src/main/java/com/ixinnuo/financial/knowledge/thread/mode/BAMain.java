package com.ixinnuo.financial.knowledge.thread.mode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;

/**
 * 客户端
 * 
 * @author 2476056494@qq.com
 *
 */
public class BAMain {

	public static void main(String[] args) {
		BlockingQueue<BAPCData> queue = new LinkedBlockingDeque<BAPCData>(100);
		ExecutorService newThreadPool = BBNewThreadPool.newThreadPool(5);
		// 10个生产者
		for (int i = 0; i < 10; i++) {
			newThreadPool.execute(new BAProducer(queue));
		}
		// 2个消费者
		for (int i = 0; i < 2; i++) {
			newThreadPool.execute(new BAConsumer(queue));
		}
		newThreadPool.shutdown();
		while (!newThreadPool.isTerminated()) {
			// 阻塞等待
		}
		System.out.println("任务结束");
	}

}
