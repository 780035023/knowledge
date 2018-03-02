package com.ixinnuo.financial.knowledge.thread.lock;




/**
 * 
 * ThreadLocal最重要的三个方法set，get，remove
 * ===================================
 * 
 *  ThreadLocalMap getMap(Thread t) {
        return t.threadLocals;
    }   
    
    void createMap(Thread t, T firstValue) {
    	//内部持有entry，简单理解为map即可
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }

    
    
 *     ThreadLocal的set方法
 *     public void set(T value) {
 *     //得到当前线程对象
        Thread t = Thread.currentThread();
        //获取ThreadLocalMap，看getMap源码，这个ThreadLocalMap是线程对象本身持有的
        //而且初次获取肯定是null，需要初始化，看createMap源码
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);//ThreadLocalMap已经存在，放值，key是线程本身
        else
            createMap(t, value);//ThreadLocalMap未初始化，初始化，并放置
       }
       
    ===================================    
		get方法
		
		public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        //从当前线程获取ThreadLocalMap
        if (map != null) {
        	//从ThreadLocalMap.Entry中获取当前线程对应的值，简单理解为map
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        //在set之前调用get，map为空，返回初始值null，通常自行实现改方法
        return setInitialValue();
    }
    
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;

/**
 * 多个线程使用的同一个SimpleDateFormat，导致线程不安全问题
 * 
 * @author 2476056494@qq.com
 *
 */
public class BAThreadLocal {

	static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) {
		// 创建线程池
		ExecutorService newThreadPool = BBNewThreadPool.newThreadPool(2);
		for (int i = 0; i < 10; i++) {
			// 1.格式化日期，不使用ThreadLocal
			// ParseDate parsedate = new ParseDate((2000 + i) + "-02-28");
			// 2.错误的使用threadlocal
			// ParseDateWrong parsedate = new ParseDateWrong((2000 + i) +
			// "-02-28");
			// 3.正确的使用threadlocal
			ParseDateRight parsedate = new ParseDateRight((2000 + i) + "-02-28");
			newThreadPool.execute(parsedate);
		}
		newThreadPool.shutdown();

	}

	/**
	 * 【1】格式化日期，不使用ThreadLocal的情况下 多个线程使用的同一个SimpleDateFormat，导致线程不安全问题
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ParseDate implements Runnable {

		private String source;

		public ParseDate(String source) {
			this.source = source;
		}

		@Override
		public void run() {
			try {
				// 因为SimpleDateFormat不是线程安全的，下面语句会报错java.lang.NumberFormatException:
				// multiple points
				Date parse = format.parse(source);
				System.out.println(Thread.currentThread().getName() + ":" + parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 【2】虽然使用了threadLocal，但是每个线程持有的是同一个SimpleDateFormat，仍然有线程安全问题
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ParseDateWrong implements Runnable {

		private String source;

		public ParseDateWrong(String source) {
			this.source = source;
		}

		@Override
		public void run() {
			try {
				//
				SimpleDateFormat format = threadLocal.get();
				if (null == format) {
					threadLocal.set(BAThreadLocal.format);
					format = threadLocal.get();
				}
				Date parse = format.parse(source);
				System.out.println(Thread.currentThread().getName() + ":" + parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 【3】threadLocal是一个容器，一般定义在线程外部静态的，方便线程内部set和get
	 * set放进threadLocalMap，key是线程本身，value是设置的值 get只能取到本身线程set进去的值
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class ParseDateRight implements Runnable {

		private String source;

		public ParseDateRight(String source) {
			this.source = source;
		}

		@Override
		public void run() {
			try {
				// threadLocal放进去的一定不是其他线程的资源，否则依然有线程问题
				SimpleDateFormat format = BAThreadLocal.threadLocal.get();
				if (null == format) {
					BAThreadLocal.threadLocal.set(new SimpleDateFormat("yyyy-MM-dd"));
					format = BAThreadLocal.threadLocal.get();
				}
				Date parse = format.parse(source);
				System.out.println(Thread.currentThread().getName() + ":" + parse);
			} catch (ParseException e) {
				e.printStackTrace();
			} finally {
				// 使用了线程池，threadLocal中的数据要remove，一直存在的线程不会自动回收
				BAThreadLocal.threadLocal.remove();
			}

		}
	}
}
