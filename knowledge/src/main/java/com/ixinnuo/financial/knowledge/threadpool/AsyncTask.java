package com.ixinnuo.financial.knowledge.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());    
    
    // myTaskAsynPool即配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    //异常要抛出去
    @Async("myTaskAsyncPool") 
    public void doTask1(int i){  
        try {
            Thread.sleep(1000);//耗时1秒
            logger.info("Task {} started.",i);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }    
    }   
}
