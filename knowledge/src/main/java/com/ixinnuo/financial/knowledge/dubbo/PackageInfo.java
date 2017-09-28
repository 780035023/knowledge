package com.ixinnuo.financial.knowledge.dubbo;

public class PackageInfo {

	/*
	 * 1.服务端和客户端共享接口，可把接口用maven管理，服务端的接口可以不托管
	 * 2.注册中心不是必须的，可以指定url，点对点直连
	 * 3.springboot的devtool导致的接口不可见，或者classCastException等问题
	 */
	/**
	 * 原因是采用spring-boot-devtools会存在两个classloader，
	 * 一个是应用类加载器AppClassLoader，用于加载第三方jar
	 * 一个RestartClassLoader，用于加载用户目录下的class。即源码编译的
	 * 使用dubbo时，会为每个service创建代理类，
	 * com.alibaba.dubbo.common.bytecode.Proxy创建代理类时，
	 * 是用proxy所在classloader即AppClassLoader，去加载用户目录下的class，自然就会报class不可见。
	 * 解决方案1，粗暴简单，不使用devtools插件，但是没有解决根本问题
	 * 解决方案2，在/src/main/resources/META-INF/spring-devtools.properties文件中，指定共享接口和dubbo的类加载为restart，如下
	 * restart.include.dubbo=/dubbo-[\\d\\.]+\\.jar
	 * restart.include.knowledge=/knowledge.jar
	 */
}
