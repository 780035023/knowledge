package com.ixinnuo.financial.knowledge.thread.lock;

/**
 * 锁的持有时间越长，资源竞争越激烈，减少锁的持有时间
 * 
 * @author 2476056494@qq.com
 *
 */
public class AALockHoldTime {

	private void othercode() {

	}

	private void mutexmethod() {

	}

	// 【1】整个方法加锁，其中othercode并没有资源冲突问题，导致持有锁的时间加长
	synchronized void method1() {
		othercode();
		mutexmethod();
		othercode();
	}

	// 【2】只把有资源冲突的加锁
	void method2() {
		othercode();
		synchronized (this) {
			mutexmethod();
		}
		othercode();
	}

}
