package com.ixinnuo.financial.java8.lambda;

import java.util.ArrayList;
import java.util.List;
/**
 * java8的集合对象新增forEach方法，接收一个参数java.util.function.Consumer
 * 这个Consumer也是java8新增的接口方法，接收一个单一参数，没有任何返回值，需要完成的功能自行实现
 * @author 2476056494@qq.com
 *
 */
public class ACLambdaForeach {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		//声明item变量为list的元素，
		/*其源码实现就是for each遍历集合本身，然后Consumer来处理每一个元素的具体功能
	default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
		 */
		list.forEach(item->System.out.println(item));
	}
}
