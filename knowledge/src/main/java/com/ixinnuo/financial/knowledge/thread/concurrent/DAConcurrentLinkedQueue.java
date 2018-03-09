package com.ixinnuo.financial.knowledge.thread.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

import net.bytebuddy.utility.RandomString;

/**
 * 
 * 首先了解下Queue和Deque的区别，都是队列的一种，队列的应用场景，一般都是生成消费模式下</br>
 * Queue 只能在队尾添加元素，在队首获取元素</br>
 * add(E e)和offer(E e)都是在队尾添加元素，区别add失败会抛出异常，不会返回false，offer失败返回false
 * remove和poll都是在队首移除元素，区别remove空队列会抛出异常，poll返回null
 * element和peek，都是在队首获取元素的值，但不移除元素，区别element空队列会抛出异常，peek返回null
 * 一般使用offer，poll和peek
 * <hr>
 * </br>
 * Deque 可以在队列两端分别添加和移除元素，如果限定只能在一端操作元素就是桟的一种形式先进后出</br>
 * 方法都加上了first和last进行区分/br>
 * <hr>
 * </br>
 * ConcurrentLinkedQueue是一种基于链表实现的无界、非阻塞[cas实现]、线程安全、队列，遵循先入先出规则。适用单生产多消费
 * https://segmentfault.com/a/1190000011094725
 * 
 * @author 2476056494@qq.com
 *
 */
public class DAConcurrentLinkedQueue {

	// 无界队列里面放置人名，抢购成功的，后续成立订单
	volatile static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();

	public static void main(String[] args) {
		//最大处理能力10的线程池，可改变生产量的大小来观察超过最大能力后的拒绝情况
		ExecutorService threadPool = BBNewThreadPool.newThreadPool(2);
		for(int i=0;i<30;i++){
			//不能使用线程池提交任务的方式，不一定马上执行，这里直接当成普通方法调用
			new ProducerTask().run();
		}
		while(!queue.isEmpty()){
			//消费者可以多线程
			threadPool.execute(new consumerTask());
		}
		threadPool.shutdown();
		while(!threadPool.isTerminated()){
		}
		
		System.out.println("抢购结束");
	}

	/**
	 * 错误的使用ConcurrentLinkedQueue方式，线程池提交任务<br>
	 * 线程池的任务并不是马上执行的，也就是说，往队列里添加数据不一定马上成功<br>
	 * 那么在消费的时候，可能就没有数据，导致前后不一致问题，<br>
	 * 除非等待生产任务执行完毕之后再执行消费，{@link DBLinkedBlockingQueue}<br>
	 * 生产任务 抢购的用户
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ProducerTask implements Runnable {

		@Override
		public void run() {
			String make = RandomString.make(5);
			queue.offer(make);
			System.out.println("添加"+make);
		}
	}
	
	public static class consumerTask  implements Runnable {
		@Override
		public void run(){
			if(!queue.isEmpty()){
				System.out.println(queue.poll() + "生成订单");
			}
		}
	}

}
