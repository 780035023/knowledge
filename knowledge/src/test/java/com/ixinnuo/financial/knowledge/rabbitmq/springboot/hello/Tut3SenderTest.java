package com.ixinnuo.financial.knowledge.rabbitmq.springboot.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ixinnuo.financial.Application;
import com.ixinnuo.financial.knowledge.rabbitmq.springboot.pubsub.Tut3Sender;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext
public class Tut3SenderTest {

    @Autowired
    public Tut3Sender tut3Sender;
    
    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            tut3Sender.send(i);
        }
    }
}
