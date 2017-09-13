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
public class RedisTemplateDaoTest {

    @Autowired
    public RedisTemplateDao redisTemplateDao;
    
    
    @Test
    public void test(){
        redisTemplateDao.writeStringValue("liqq", 1000, "hello");
    }
}
