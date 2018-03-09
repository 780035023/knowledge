package com.ixinnuo.financial.knowledge.thread.mode.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 事件处理，即消费者
 * 
 * @author 2476056494@qq.com
 *
 */
public class AALongEventHandler implements EventHandler<AALongEvent> {

	public AALongEventHandler() {
		System.out.println("消费"+this + " is created...");
	}
	
	@Override
	public void onEvent(AALongEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println(this + " 消费:" + event + ";sequence: " + sequence + ";endOfBatch: " + endOfBatch);
	}
}
