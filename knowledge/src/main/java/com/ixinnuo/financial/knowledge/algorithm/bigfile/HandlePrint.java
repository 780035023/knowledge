package com.ixinnuo.financial.knowledge.algorithm.bigfile;
/**
 * 打印处理
 * @author aisino
 *
 */
public class HandlePrint implements IHandle{

	@Override
	public void handleLine(String line) {
		System.out.println(line);
	}
}
