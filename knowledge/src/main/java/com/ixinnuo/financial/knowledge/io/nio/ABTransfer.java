package com.ixinnuo.financial.knowledge.io.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ABTransfer {

	public static void main(String[] args) throws Exception {
		RandomAccessFile fromFile = new RandomAccessFile("src/main/java/com/ixinnuo/financial/knowledge/io/nio/from.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("src/main/java/com/ixinnuo/financial/knowledge/io/nio/to.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		//【1】将字节从给定的可读取字节通道传输到此通道的文件中，覆盖此文件原内容
		toChannel.transferFrom(fromChannel, fromChannel.size(), position);
		//【2】将此通道的字节传输到给定可写通道的文件中
		fromChannel.transferTo(position, fromChannel.size(), toChannel);
		fromFile.close();
		toFile.close();
	}
}
