package com.ixinnuo.financial.java8.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;

public class AALocalDateTime {

	public static void main(String[] args) {
		// 获取本地当前时间
		LocalDateTime now = LocalDateTime.now();
		System.out.println("获取本地当前时间 " + now);
		// 获取指定时间,注意月份是从1-12，小时0-23，分钟，秒0-59
		LocalDateTime instance = LocalDateTime.of(2018, 2, 27, 10, 0, 59);
		System.out.println("设置指定时间 " + instance);
		// 格式化时间
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		System.out.println("格式化时间 " + now.format(formatter));
		// 转化时间
		LocalDateTime parse = LocalDateTime.parse("2018-02-28 10:58:43", formatter);
		System.out.println("转化时间 " + parse);
		// 日期部分
		LocalDate localDate = now.toLocalDate();
		System.out.println("日期部分" + localDate);
		// 时间部分
		LocalTime localTime = now.toLocalTime();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		System.out.println("时间部分" + localTime.format(timeFormatter));
		// 获取你能想到的所有的值
		now.getYear();
		now.getMonthValue();
		now.getDayOfMonth();
		now.getDayOfWeek().getValue();
		now.getHour();
		now.getMinute();
		now.getSecond();
		// 日期减少
		LocalDateTime minusDays = now.minusDays(1);
		System.out.println("日期减少1天 " + minusDays);
		// 日期增加
		LocalDateTime plusMonths = now.plusMonths(1);
		System.out.println("日期增加1月 " + plusMonths);
		// 两个日期间隔，只返回单位的整数部分，小数忽略
		TemporalUnit DAYS = ChronoField.DAY_OF_YEAR.getBaseUnit();
		long days = now.until(instance, DAYS);
		System.out.println(now + " 到 " + instance + " 相差 " + days + " 天");
		TemporalUnit HOURS = ChronoField.HOUR_OF_DAY.getBaseUnit();
		long hours = now.until(instance, HOURS);
		System.out.println(now + " 到 " + instance + " 相差 " + hours + " 小时");
		//计算年龄
		LocalDate birthday = LocalDate.of(1985, 12, 23);
		TemporalUnit YEARS = ChronoField.YEAR.getBaseUnit();
		long year = birthday.until(now, YEARS);
		System.out.println("周岁 " + year);
	}

}
