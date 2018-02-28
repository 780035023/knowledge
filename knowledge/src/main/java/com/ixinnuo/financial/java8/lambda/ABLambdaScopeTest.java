package com.ixinnuo.financial.java8.lambda;

import java.util.function.Consumer;
/**
 * lambda可以引用外部的变量，只要是final或者等效final即可
 * @author 2476056494@qq.com
 *
 */
public class ABLambdaScopeTest {
	public int x = 0;

	class FirstLevel {

		public int x = 1;

		void methodInFirstLevel(int x) {
			//x=99;如果改变x的值，编译器任务x不是final的类型，将会出错
			
			//Consumer是java8提供的一个接口方法，接收一个单一参数，不返还任何值
			Consumer<Integer> myConsumer = (y) -> {//此处的声明y不能使用已声明的x
				System.out.println("x = " + x);
				System.out.println("y = " + y);
				System.out.println("this.x = " + this.x);
				System.out.println("LambdaScopeTest.this.x = " + ABLambdaScopeTest.this.x);
			};

			myConsumer.accept(x);

		}
	}

	public static void main(String... args) {
		ABLambdaScopeTest st = new ABLambdaScopeTest();
		ABLambdaScopeTest.FirstLevel fl = st.new FirstLevel();
		fl.methodInFirstLevel(23);
	}
}
