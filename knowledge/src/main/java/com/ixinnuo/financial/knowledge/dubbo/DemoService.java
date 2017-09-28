package com.ixinnuo.financial.knowledge.dubbo;

import java.io.Serializable;

public interface DemoService extends Serializable{

	String sayHello(String name);
}
