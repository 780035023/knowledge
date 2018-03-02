package com.ixinnuo.financial.java8.datetime;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

public class AALocalDateTime {

	public static void main(String[] args) {
		// 获取本地当前时间
		LocalDateTime now = LocalDateTime.now();
		System.out.println("获取本地当前时间 " + now);
		OffsetDateTime offdt = OffsetDateTime.now();
		System.out.println("获取本地当前时间带时间偏移量 " + offdt);
		ZonedDateTime zonednow = ZonedDateTime.now();
		System.out.println("获取本地当前时间带时区 " + zonednow);
		
		
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
		TemporalUnit DAYS = ChronoUnit.DAYS;
		long days = now.until(instance, DAYS);
		System.out.println(now + " 到 " + instance + " 相差 " + days + " 天");
		TemporalUnit HOURS = ChronoUnit.HOURS;
		long hours = now.until(instance, HOURS);
		System.out.println(now + " 到 " + instance + " 相差 " + hours + " 小时");
		//计算年龄
		LocalDate birthday = LocalDate.of(1985, 12, 23);
		TemporalUnit YEARS = ChronoUnit.YEARS;
		long year = birthday.until(now, YEARS);
		System.out.println("周岁 " + year);
		
		
		LocalDateTime withDayOfMonth = now.withDayOfMonth(5);
		System.out.println("日期所处月份的第5天 " + withDayOfMonth);
		LocalDateTime with = now.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println("日期所处月份的最后1天 " + with);
		LocalDateTime monday = now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
		System.out.println("日期所处月份的第1个周一 " + monday);
		//时间偏移量,中国东8区，偏移量为正8个小时
		ZoneOffset zoneOffset = ZoneOffset.of("+08:00");
		//本地时间转成瞬时，确定时区偏移量，最后转毫秒
		long milli = now.toInstant(zoneOffset).toEpochMilli();
		System.out.println(now + " 转毫秒 " + milli);
		//毫秒转瞬时，再确定时区偏移量，最后转本地时间
		LocalDateTime localDateTime = Instant.ofEpochMilli(milli).atOffset(zoneOffset).toLocalDateTime();
		System.out.println(milli + " 转日期 " + localDateTime);
		
		
		
	}

}
