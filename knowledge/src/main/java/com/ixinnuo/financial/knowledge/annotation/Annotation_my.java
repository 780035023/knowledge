package com.ixinnuo.financial.knowledge.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE,ElementType.FIELD })
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Annotation_my {

	String name() default "张三";// defalt 表示默认值

	String say() default "hello world";

	int age() default 21;

}