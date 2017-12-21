package com.ixinnuo.financial.knowledge.algorithm.bigfile;
/**
 * 打印处理
 * @author 2476056494@qq.com
 *
 */
public class HandlePrint implements IHandle{

	@Override
	public void handleLine(String line) {
		System.out.println(line);
	}
}
