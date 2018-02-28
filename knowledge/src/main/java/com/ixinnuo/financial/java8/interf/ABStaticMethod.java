package com.ixinnuo.financial.java8.interf;

import java.time.DateTimeException;
import java.time.ZoneId;
/**
 * 接口静态方法，必须实现，它属于类所有
 * @author 2476056494@qq.com
 *
 */
public class ABStaticMethod {

	public interface TimeClient {
		/**
		 * 得到对应的zoneid对象
		 * @param zoneString
		 * @return
		 */
		static public ZoneId getZoneId(String zoneString) {
			try {
				return ZoneId.of(zoneString);
			} catch (DateTimeException e) {
				System.err.println("Invalid time zone: " + zoneString + "; using default time zone instead.");
				return ZoneId.systemDefault();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(TimeClient.getZoneId("Asia/Shanghai"));
		System.out.println(TimeClient.getZoneId("Asia/Beijing"));
	}
}
