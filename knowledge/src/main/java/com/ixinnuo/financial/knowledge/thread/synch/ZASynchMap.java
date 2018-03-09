package com.ixinnuo.financial.knowledge.thread.synch;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * map变为线程安全的Collections.synchronizedMap，内部增加一个同步对象，所有方法都需要获取对象锁
 * @author 2476056494@qq.com
 *
 */
public class ZASynchMap {

	
	public static void main(String[] args) {
		Map synchronizedMap = Collections.synchronizedMap(new HashMap());
		synchronizedMap.put("a", "1");
		System.out.println(synchronizedMap.get("a"));
	}
	
	
}
