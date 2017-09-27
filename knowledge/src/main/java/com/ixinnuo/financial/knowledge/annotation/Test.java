package com.ixinnuo.financial.knowledge.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
	Annotation[] annotation = null;

	public static void main(String[] args) throws ClassNotFoundException {
		new Test().getAnnotation();
	}

	public void getAnnotation() throws ClassNotFoundException {
		Class<?> stu = Class.forName("com.ixinnuo.financial.util.annotation.type.Student");// 静态加载类
		// 判断stu是不是使用了我们刚才定义的注解接口,spring 容器启动时，初始化component注解的类，原理
		boolean isEmpty = stu.isAnnotationPresent(Annotation_my.class);
		annotation = stu.getAnnotations();// 当前元素为类，只能获取类上的注解，方法，属性获取不到
		for (Annotation a : annotation) {
			Annotation_my my = (Annotation_my) a;// 强制转换成Annotation_my类型
			System.out.println(stu + ":\n" + my.name() + " say: " + my.say() + " my age: " + my.age());
		}

		Method[] method = stu.getMethods();//
		System.out.println("Method");
		for (Method m : method)

		{
			boolean ismEmpty = m.isAnnotationPresent(Annotation_my.class);
			if (ismEmpty) {
				Annotation[] aa = m.getAnnotations();// 当前元素为方法，只能获取该方法上的注解
				for (Annotation a : aa) {
					Annotation_my an = (Annotation_my) a;
					System.out.println(m + ":\n" + an.name() + " say: " + an.say() + " my age: " + an.age());
				}
			}
		}
		// get Fields by force
		System.out.println("get Fileds by force !");

		Field[] field = stu.getDeclaredFields();
		for (Field f : field)

		{
			f.setAccessible(true);
			System.out.println(f.getName());
			boolean fieldB = f.isAnnotationPresent(Annotation_my.class);
			Annotation[] annotations = f.getAnnotations();
			for (Annotation a : annotations) {
				Annotation_my an = (Annotation_my) a;
				System.out.println(f + ":\n" + an.name() + " say: " + an.say() + " my age: " + an.age());
			}
		}
		System.out.println("get methods in interfaces !");

		/* 获取类型的接口 */
		Class<?> interfaces[] = stu.getInterfaces();
		for (Class<?> c : interfaces)

		{
			Method[] imethod = c.getMethods();
			for (Method m : imethod) {
				System.out.println(m.getName());
			}
		}
	}

}