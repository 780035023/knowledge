package com.ixinnuo.financial.knowledge.thread.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * 
 * {@link ConcurrentHashMap} 减少锁粒度,put分成16段分别加锁
 * {@link ReadWriteLock} 读写锁替换独占锁
 * {@link LinkedBlockingQueue} 锁分离，take前端取数和put后端放数，对应两个锁
 * 锁粗化，频繁的对资源加锁，释放，便会被整合成一次锁的请求释放，如for循环内部的加锁，应该放到外部加锁
 * @author 2476056494@qq.com
 *
 */
public class ABLockSize {

	
}
