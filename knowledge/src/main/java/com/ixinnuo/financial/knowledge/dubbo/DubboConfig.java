package com.ixinnuo.financial.knowledge.dubbo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration  
@PropertySource("classpath:application-test.properties")
@ImportResource({ "classpath:dubbo/*.xml" })
public class DubboConfig {

}
