package com.ixinnuo.financial.conf.intercept;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ClientWebAppConfigurer extends WebMvcConfigurerAdapter{
	
	@Autowired
	ClientInterceptor clientInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(clientInterceptor).addPathPatterns("/**");

        super.addInterceptors(registry);
		super.addInterceptors(registry);
	}

}
