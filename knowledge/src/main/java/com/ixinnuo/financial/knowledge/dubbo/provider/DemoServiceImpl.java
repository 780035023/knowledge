package com.ixinnuo.financial.knowledge.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ixinnuo.financial.knowledge.dubbo.DemoService;

public class DemoServiceImpl implements DemoService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public String sayHello(String name) {
		logger.info("服务提供端执行");
		return "Hello " + name;
	}
}
