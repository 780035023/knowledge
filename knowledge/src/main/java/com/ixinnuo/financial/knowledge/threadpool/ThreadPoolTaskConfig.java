package com.ixinnuo.financial.knowledge.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 配置类，引入配置项
 * 
 * @author aisino 
 *         如果此时线程池中的数量小于corePoolSize，即使线程池中的线程都处于空闲状态，也要创建新的线程来处理被添加的任务。
 *         如果此时线程池中的数量等于 corePoolSize，但是缓冲队列 workQueue未满，那么任务被放入缓冲队列。
 *         如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量小于maxPoolSize，
 *         建新的线程来处理被添加的任务。
 *         如果此时线程池中的数量大于corePoolSize，缓冲队列workQueue满，并且线程池中的数量等于maxPoolSize，
 *         那么通过handler所指定的策略来处理此任务。
 *         处理任务的优先级为：核心线程corePoolSize、任务队列workQueue、超过最大线程 maximumPoolSize时的主线程，
 *         
 *         一般设置关系如下
 *         缓存队列=最小*2
 *         最大=最小*3
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
// 必须加此注解，否则线程池不起作用，仍在主线程执行
@EnableAsync
public class ThreadPoolTaskConfig {

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    // ExecutorService 'myTaskAsyncPool'
    // 
    @Bean
    public Executor myTaskAsyncPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("MyExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行 【主线程会执行任务】
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
