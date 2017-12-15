package com.ixinnuo.financial.knowledge.algorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BigDataFrequency {

	public static void main(String[] args) {
		BigDataFrequency bigDataFrequency = new BigDataFrequency();
		bigDataFrequency.breakUp("C:\\Users\\aisino\\Desktop\\new 1.txt",2000);

	}

	// 1.分割文件,大文件切割需要多线程
	public void breakUp(String bigFilePath,int division) {

		BufferedReader in;
		BufferedWriter out;
		try {
			File bigFile = new File(bigFilePath);
			String parentPath = bigFile.getParent();
			// 创建子目录，存放分割的小文件
			File smallFilePath = new File(parentPath + File.separator + "breakUP");
			smallFilePath.mkdir();
			in = new BufferedReader(new FileReader(bigFile));
			String line;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				int smallFileName = line.hashCode() % division;
				File smallFile = new File(smallFilePath, smallFileName + "");
				if (!smallFile.exists()) {
					smallFile.createNewFile();
				}
				//追加内容
				out = new BufferedWriter(new FileWriter(smallFile, true));
				out.write(line + "\r\n");// 换行 兼容windows和linux
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
