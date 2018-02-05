package com.ixinnuo.financial.knowledge.thread.concurrent;
/**
 * 没有办法精确线程池的大小，只要避免极大极小即可，需要指定cpu使用率，因为可能一台服务器上不止一个线程池
 * 为保持cpu的使用率，计算线程池合适的大小 nThread = 可用的cpu个数 * 指定的cpu使用率 * (1 + 非cpu等待时间/cpu计算时间)
 * 简单的一种结论是：如果任务是cpu密集型的，就设置为可用的cpu个数+1；如果是io密集型的就设置为2倍的cpu个数
 * @author 2476056494@qq.com
 *
 */
public class BDNThreads {

	
	public static void main(String[] args) {
		//可用的cpu个数
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println("可用的cpu" + availableProcessors);
		//分配内存
		long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
		System.out.println("分配内存M" + totalMemory);
		//可用的内存，兆
		long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
		System.out.println("可用的内存M" + freeMemory);
		//最大使用的内存
		long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
		System.out.println("最大可使用的内存M" + maxMemory);
	}
}
