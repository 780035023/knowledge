package com.ixinnuo.financial.util.annotation;

@Annotation_my
public class Student {

	@Annotation_my(name = "流氓公子1号") 
	private String name;

	@Annotation_my(name = "流氓公子") // 赋值给name 默认的为张三
	// 在定义注解时没有给定默认值时，在此处必须name赋初值
	public void name() {

	}

	@Annotation_my(say = " hello world  ！")
	public void say() {

	}

	@Annotation_my(age = 20)
	public void age() {

	}
}