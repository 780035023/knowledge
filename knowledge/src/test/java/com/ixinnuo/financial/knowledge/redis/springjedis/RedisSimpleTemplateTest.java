package com.ixinnuo.financial.knowledge.redis.springjedis;

import java.util.ArrayList;
import java.util.List;

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
    public void testSetString(){
        redisSimpleTemplate.setString("food", "食物", null, null);
    }
    
    @Test
    public void testGetString(){
        System.out.println( redisSimpleTemplate.getString("food"));
    }
    
    @Test
    public void testSetHash(){
        System.out.println( redisSimpleTemplate.setHash("hashMap", "name", "李四"));
    }
    
    @Test
    public void testgetHash(){
        System.out.println( redisSimpleTemplate.getHash("hashMap", "name"));
    }
    @Test
    public void testSetList(){
        List list = new ArrayList();
        list.add("语文");
        list.add("数学");
        System.out.println( redisSimpleTemplate.setList("list", list));
    }
    @Test
    public void testgetList(){
        System.out.println( redisSimpleTemplate.getList("list", 0,1));
    }
}
