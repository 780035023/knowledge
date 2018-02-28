package com.ixinnuo.financial.java8.interf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
/**
 * 接口默认方法必须实现，它属于对象实例所有
 * @author 2476056494@qq.com
 *
 */
public class AADefaultMethod {

	/**
	 * 【1】定义接口
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	interface TimeClient {
		void setDate(int day, int month, int year);

		LocalDateTime getLocalDateTime();

		default ZonedDateTime getZonedDateTime(ZoneId zone) {
			return ZonedDateTime.of(getLocalDateTime(), zone);
		}
	}

	/**
	 * 【2】定义实现类
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class SimpleTimeClient implements TimeClient {
		private LocalDateTime dateAndTime;

		@Override
		public void setDate(int day, int month, int year) {
			LocalDate dateToSet = LocalDate.of(day, month, year);
			LocalTime currentTime = LocalTime.from(dateAndTime);
			dateAndTime = LocalDateTime.of(dateToSet, currentTime);

		}

		@Override
		public LocalDateTime getLocalDateTime() {
			if (null == dateAndTime) {
				dateAndTime = LocalDateTime.now();
			}
			return dateAndTime;
		}

	}

	public static void main(String... args) {
		TimeClient myTimeClient = new SimpleTimeClient();
		System.out.println(myTimeClient.getLocalDateTime());
		//【3】default方法可以直接用
		System.out.println(myTimeClient.getZonedDateTime(ZoneId.systemDefault()));
	}
	
	//可以通过接口继承来扩展默认方法
//	1.根本没有提及默认方法，这可以让您的扩展接口继承默认方法。其他实现类可以直接使用该方法
//	2.重新声明默认的方法，这使得它变为abstract。其他实现类必须实现该方法
//	3.重写覆盖它的默认方法。其他实现类使用该方法是重写后的
}
