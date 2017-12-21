package com.ixinnuo.financial.knowledge.algorithm.bigfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 哈希处理
 * @author 2476056494@qq.com
 *
 */
public class HandleHash implements IHandle{
	
	/**
	 * 大文件所在位置，方便在同目录下操作
	 */
	public File bigFile;
	/**
	 * 除数
	 */
	public int divisor;

	@Override
	public void handleLine(String line) {
		System.out.println(line);
		String parentPath = bigFile.getParent();
		// 创建子目录，存放分割的小文件
		File smallFilePath = new File(parentPath + File.separator + "breakUp");
		if(!smallFilePath.exists()){
			smallFilePath.mkdir();
		}
		int smallFileName = line.hashCode() % divisor;
		File smallFile = new File(smallFilePath, smallFileName + "");
		try {
			if (!smallFile.exists()) {
				smallFile.createNewFile();
			}
			//追加内容
			BufferedWriter out = new BufferedWriter(new FileWriter(smallFile, true));
			out.write(line + "\r\n");// 换行 兼容windows和linux
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
