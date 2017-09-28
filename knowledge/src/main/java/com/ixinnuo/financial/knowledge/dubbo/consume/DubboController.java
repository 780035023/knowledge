package com.ixinnuo.financial.knowledge.dubbo.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixinnuo.financial.framework.Code;
import com.ixinnuo.financial.framework.ReturnData;
import com.ixinnuo.financial.knowledge.dubbo.DemoService;

@Controller
@RequestMapping("/dubbo")
public class DubboController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//虽然是一个项目，但是并没有使用@service注解，而是来自dubbo的rpc
	@Autowired
	private DemoService demoService;
	
	@RequestMapping(value = "sayHello",method=RequestMethod.GET)
	@ResponseBody
	public ReturnData sayHello(String name){
		logger.info("服务消费端执行");
		String sayHello = demoService.sayHello(name);
		ReturnData returnData = new ReturnData(Code.OK);
		returnData.getDataBody().put("sayHello", sayHello);
		return returnData;
	}
}
