package com.ixinnuo.financial.knowledge.dubbo.provider;

import com.ixinnuo.financial.knowledge.dubbo.DemoService;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
