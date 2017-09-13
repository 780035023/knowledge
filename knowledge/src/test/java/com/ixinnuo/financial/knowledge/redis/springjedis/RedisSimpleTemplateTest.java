package com.ixinnuo.financial.knowledge.redis.springjedis;

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
public class RedisSimpleTemplateTest {

    @Autowired
    public RedisSimpleTemplate redisSimpleTemplate;
    
    
    @Test
    public void test(){
        redisSimpleTemplate.setString("food", "食物", null, null);
    }
}
