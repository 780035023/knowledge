package com.ixinnuo.financial.knowledge.algorithm.bigfile;

/**
 * 小文件的开始结束位置，保证开始位置是行首，结束位置是行末尾
 * @author aisino
 *
 */
public class StartEndPair {
	public long start;
	public long end;

	@Override
	public String toString() {
		return "star=" + start + ";end=" + end;
	}


}