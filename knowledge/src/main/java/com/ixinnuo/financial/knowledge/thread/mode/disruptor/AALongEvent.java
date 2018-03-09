package com.ixinnuo.financial.knowledge.thread.mode.disruptor;

/**
 * 定义事件，携带数据
 * 
 * @author 2476056494@qq.com
 *
 */
public class AALongEvent {

	private long value;

	public void set(long value) {
		this.value = value;
	}
}
