package com.ixinnuo.financial.knowledge.thread.mode.disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

public class AALongEventMain {

	public static void main(String[] args) throws Exception {
		// 线程池
		Executor executor = Executors.newCachedThreadPool();

		// 事件工厂
		AALongEventFactory factory = new AALongEventFactory();

		// 环形队列的大小，必须是2的n次方
		int bufferSize = 4;

		// 【1】构造disruptor，用到事件工厂和线程池，并指定队列大小
		//Disruptor<AALongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);
		Disruptor<AALongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor,ProducerType.SINGLE,new BlockingWaitStrategy());

		//【2】disruptor连接消费者，等待队列中的事件写入数据
		disruptor.handleEventsWith(new AALongEventHandler());

		// 【3】disruptor启动，队里填充满事件
		disruptor.start();

		// disruptor中的环形队列
		RingBuffer<AALongEvent> ringBuffer = disruptor.getRingBuffer();
		//【4】生产，向队列中的事件写数据，这里用了两个生产者
		AALongEventProducer producer = new AALongEventProducer(ringBuffer);
		AALongEventProducer producer2 = new AALongEventProducer(ringBuffer);

		for (long l = 0; true; l++) {
			producer.onData(l);
			producer2.onData(l*2);
			Thread.sleep(10000);
		}
	}
}
