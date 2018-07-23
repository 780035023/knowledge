package com.ixinnuo.financial.knowledge.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("run")
public class RunController {

	@Autowired
	private JoinPoint joinPoint;
	
	@GetMapping("runAop")
	@ResponseBody
	public String runAop(){
		joinPoint.say("hanmeimei");
		return "ok";
	}
}
