package com.ixinnuo.financial.java8.lambda;

/**
 * Lambda语法，(逗号分割的参数列表，单个参数可省略外面括号)->{单个表达式或语句块，可以在返回值处加return}
 * 使用场景：定义方法的最后一个参数是接口（只能有一个方法，排除默认和静态的）,其他参数对应接口方法的参数，Lambda当成实现接口的匿名类使用
 * 
 * @author 2476056494@qq.com
 *
 */
public class AACalculator {

	/**
	 * 【1】定义接口方法
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	interface IntegerMath {
		int operation(int a, int b);
	}

	/**
	 * 【2】使用接口方法作为参数
	 * 
	 * @return
	 */
	public int operateBinary(int c, int d, IntegerMath op) {
		return op.operation(c, d);
	}

	public static void main(String... args) {

		AACalculator myApp = new AACalculator();
		//【3】lambda表达式实现接口方法
		IntegerMath addition = (a, b) -> a + b;
		IntegerMath subtraction = (a, b) -> {
			int c = a - b;
			System.out.println(a + " - " + b + " = " + c);
			return c;
		};
		System.out.println("40 + 2 = " + myApp.operateBinary(40, 2, addition));
		myApp.operateBinary(20, 10, subtraction);
		//【4】lambda表达式也可以直接写在方法的入参上，完全同匿名类
		int a=100,b=200;
		System.out.println("100 + 200 = " + myApp.operateBinary(a, b, (int m, int n) -> m + n));
	}
}