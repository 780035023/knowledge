package com.ixinnuo.financial;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * springboot 官方文档：
 * http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
 * @author zhangkm
 *
 */
//springboot在/static, /public, META-INF/resources, /resources等存放静态资源的目录
@Controller
@SpringBootApplication
@MapperScan(basePackages = "com.ixinnuo.financial.dao.mysql")
@EnableTransactionManagement
public class Application extends WebMvcConfigurerAdapter {
    /**
     * 工程程序入口
     * @Description: 工程程序入口
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/")
    String home() {
        //return "redirect:/swagger-ui.html";
        return "redirect:/html/index.html";
    }
    
}