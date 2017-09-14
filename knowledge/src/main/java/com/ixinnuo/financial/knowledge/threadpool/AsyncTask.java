package com.ixinnuo.financial.knowledge.threadpool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    // myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    // 异常要抛出去
    @Async("myTaskAsyncPool")
    public void doTask(int i) {
        try {
            Thread.sleep(1000);// 耗时1秒
            logger.info("Task {} started.", i);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async("myTaskAsyncPool")
    public Future<String> doTask1(int i) throws InterruptedException {
        logger.info("Task1 {} started.", i);
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();

        logger.info("Task1 {} finished, time elapsed: {} ms.", i,end - start);

        return new AsyncResult<>("Task1 "+i+" accomplished!");
    }
    
    @Async("myTaskAsyncPool")
    public CompletableFuture<String> doTask2(int i) throws InterruptedException {
        logger.info("Task2 {} started.", i);
        long start = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        
        logger.info("Task2 {} finished, time elapsed: {} ms.", i,end - start);
        
        return CompletableFuture.completedFuture("Task2 "+i+" accomplished!");
    }

}
