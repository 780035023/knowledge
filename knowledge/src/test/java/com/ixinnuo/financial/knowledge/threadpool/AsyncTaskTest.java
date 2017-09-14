package com.ixinnuo.financial.knowledge.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

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
    
    
    @Test
    public void testDoTask() throws InterruptedException{
        for (int i = 0; i < 30; i++) {  
            asyncTask.doTask(i);  
        } 
        //测试用，等待任务结束再终止
        System.out.println("all task finished");
        Thread.sleep(1000*15);
    }
    
    @Test
    public void testDoTask1() throws InterruptedException, ExecutionException{
        List<Future> list = new ArrayList<Future>(); 
        for (int i = 0; i < 30; i++) {  
            Future<String> doTask1 = asyncTask.doTask1(i); 
            list.add(doTask1);
        } 
        System.out.println("all task commited");
        for(int i = 0;i<list.size();i++){
            Future future = list.get(i);
            System.out.println(future.get());
        }
        //测试用，等待任务结束再终止
        System.out.println("all task finished");
    }
    @Test
    public void testDoTask2() throws InterruptedException, ExecutionException{
        List<Future> list = new ArrayList<Future>(); 
        for (int i = 0; i < 30; i++) {  
            Future<String> doTask2 = asyncTask.doTask2(i); 
            list.add(doTask2);
        } 
        System.out.println("all task commited");
        for(int i = 0;i<list.size();i++){
            Future future = list.get(i);
            System.out.println(future.get());
        }
        //测试用，等待任务结束再终止
        System.out.println("all task finished");
    }
}
