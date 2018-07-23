package com.ixinnuo.financial.knowledge.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect
@Configuration
@EnableAspectJAutoProxy
public class BeforeExample {
	
	Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.ixinnuo.financial.knowledge.aop.JoinPoint.say(..))")
    public void doAccessCheck(JoinPoint jp) {
    	log.info("BeforeExample... ");
    	Object[] args = jp.getArgs();
    	for (Object object : args) {
			log.info("参数{}",args);
		}
    }

}