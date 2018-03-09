package com.ixinnuo.financial.knowledge.thread.mode.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

/**
 * 事件 生产者
 * 
 * @author 2476056494@qq.com
 *
 */
public class AALongEventProducer {

	private final RingBuffer<AALongEvent> ringBuffer;

	public AALongEventProducer(RingBuffer<AALongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
		System.out.println("生产"+this + " is created...");
	}

	public void onData(long bb) {
		// 【1】获取序列号
		long sequence = ringBuffer.next();
		try {
			//【2】获取序列号上的事件，并传递数据
			AALongEvent event = ringBuffer.get(sequence);
			event.set(bb); // Fill with data
			System.out.println("生产" +this+" 写入事件 "+event+" 数据 " + bb);
		} finally {
			//【3】发布序列号
			ringBuffer.publish(sequence);
		}
	}
}
