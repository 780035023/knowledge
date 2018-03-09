package com.ixinnuo.financial.knowledge.thread.mode.disruptor;

import com.lmax.disruptor.EventFactory;
/**
 * 事件工厂，交给disruptor来分配事件用
 * @author 2476056494@qq.com
 *
 */
public class AALongEventFactory implements EventFactory<AALongEvent> {

	@Override
	public AALongEvent newInstance() {
		AALongEvent aaLongEvent = new AALongEvent();
		System.out.println(aaLongEvent + " is created...");
		return aaLongEvent;
	}
}
