package com.ixinnuo.financial.knowledge.threadpool;

import java.util.concurrent.Executor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ixinnuo.financial.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext
public class AsyncTaskTest {

    @Autowired
    private AsyncTask asyncTask;
    
    @Autowired
    private Executor executor;
    
    @Test
    public void test() throws InterruptedException{
        for (int i = 0; i < 30; i++) {  
            asyncTask.doTask1(i);  
        } 
        //测试用，等待任务结束再终止
        System.out.println("all task finished");
        Thread.sleep(1000*15);
    }
}
