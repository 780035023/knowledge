package com.ixinnuo.financial.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ixinnuo.financial.Application;
import com.ixinnuo.financial.model.Hello;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    /* 测试插入查询 **/
    @Test
    public void testAddOneAndSelectAll() {
        List<Hello> addOneAndSelectAll = helloService.addOneAndSelectAll("zhangsan");
        System.out.println(addOneAndSelectAll.get(addOneAndSelectAll.size()-1).getName());
    }
    
    /* 测试插入查询 **/
    @Test
    public void test() {
        List<Hello> addOneAndSelectAll = helloService.addOneAndSelectByPage("lisi", 1, 6);
        System.out.println(addOneAndSelectAll.get(addOneAndSelectAll.size()-1).getName());
    }
    
}
