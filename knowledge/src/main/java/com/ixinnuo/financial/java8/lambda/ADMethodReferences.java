package com.ixinnuo.financial.java8.lambda;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * java8,如果lambda表达式中只是引用已经存在的方法，双冒号语法 有四种方法参考：</br>
 * 引用静态方法 ContainingClass::staticMethodName</br>
 * 引用特定对象的实例方法 containingObject::instanceMethodName</br>
 * 引用特定类型任意对象的实例方法 ContainingType::methodName</br>
 * 引用构造函数 ClassName::new</br>
 * 
 * @author 2476056494@qq.com
 *
 */
public class ADMethodReferences {
	// 【3】这个静态方法就是【1】的实现即函数式接口的实现，等效的
	static int compare(String org0, String org1) {
		return org0.compareToIgnoreCase(org1);
	}

	// 【5】定义实例方法，降序
	int compare2(String org0, String org1) {
		return -org0.compareToIgnoreCase(org1);
	}

	//【8】定义使用函数式接口的方法，Supplier函数式接口get
	public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST transferElements(
			SOURCE sourceCollection, Supplier<DEST> collectionFactory) {
		DEST result = collectionFactory.get();
		for (T t : sourceCollection) {
			result.add(t);
		}
		return result;
	}

	public static void main(String[] args) {
		String[] strs = { "a", "e", "b" };
		// 【1】Comparator 是一个函数式接口，有一个未实现方法 int compare(T o1, T o2);如下lambda实现
		Comparator<String> comparator = (str1, str2) -> str1.compareToIgnoreCase(str2);
		// 【2】排序方法的参数是函数式接口Comparator
		Arrays.sort(strs, comparator);
		System.out.println(Arrays.deepToString(strs));

		// 【4】1.静态方法引用
		Arrays.sort(strs, ADMethodReferences::compare);
		System.out.println(Arrays.deepToString(strs));
		// 【6】2.实例方法引用
		ADMethodReferences obj = new ADMethodReferences();
		Arrays.sort(strs, obj::compare2);
		System.out.println(Arrays.deepToString(strs));
		// 【7】特定类型任意对象的实例方法
		Arrays.sort(strs, String::compareToIgnoreCase);
		System.out.println(Arrays.deepToString(strs));

		//【9】构造函数的引用
		Set<String> set = new HashSet<String>();
		Set<String> rosterSetLambda =
			    transferElements(set, () -> { return new HashSet<>(); });
		Set<String> rosterSet =
			    transferElements(set, HashSet::new);
	}
}
