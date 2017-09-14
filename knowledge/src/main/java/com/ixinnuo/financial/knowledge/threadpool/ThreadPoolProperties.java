package com.ixinnuo.financial.knowledge.threadpool;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.task.pool")
public class ThreadPoolProperties {

    /**
     * Set the ThreadPoolExecutor's core pool size. Default is 1. 
     */
    private int corePoolSize;

    /**
     * Set the ThreadPoolExecutor's maximum pool size. Default is Integer.MAX_VALUE. 
     */
    private int maxPoolSize;

    /**
     * Set the ThreadPoolExecutor's keep-alive seconds. Default is 60. 
     */
    private int keepAliveSeconds;

    /**
     * 设置ThreadPoolExecutor的BlockingQueue的容量。 默认值为Integer.MAX_VALUE。
     * 任何正值都会导致一个LinkedBlockingQueue实例; 任何其他值都将导致SynchronousQueue实例。
     * 一般是最大线程的2~5倍
     */
    private int queueCapacity;

    
    public int getCorePoolSize() {
        return corePoolSize;
    }

    
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    
    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    
    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    
    public int getQueueCapacity() {
        return queueCapacity;
    }

    
    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    
}
