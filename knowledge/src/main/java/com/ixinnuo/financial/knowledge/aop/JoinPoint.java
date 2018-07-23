package com.ixinnuo.financial.knowledge.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JoinPoint {
	Logger log = LoggerFactory.getLogger(this.getClass());
			
	
	public String say(String name) {
		log.info("hello:{}",name);
		return "hello" + name;
	}
}
