package com.ixinnuo.financial.knowledge.io.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
/**
 * 利用缓冲区读取文件
 * @author 2476056494@qq.com
 *
 */
public class AAChannel {
	
	private static final int bufferCapacity = 1024;

	public static void main(String[] args) throws Exception {
		// rw读写模式
		RandomAccessFile aFile = new RandomAccessFile("src/main/java/com/ixinnuo/financial/knowledge/io/nio/nio.txt",
				"rw");
		// 建立通道
		FileChannel inChannel = aFile.getChannel();
		// 缓冲区大小
		ByteBuffer buf = ByteBuffer.allocate(bufferCapacity);
		//所有字节
		byte[] allbyte = new byte[0];
		//每次读取的字节
		byte[] read = null;
		
		// 【1】读取文件--》channel--》buffer 写入buffer
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {
			read = new byte[bytesRead];
			//【2】切换模式
			buf.flip();
			//【3】buffer->out，读出buffer
			while (buf.hasRemaining()) {
				buf.get(read);
				//1.扩展总长度
				allbyte = Arrays.copyOf(allbyte, allbyte.length + bytesRead);
				//2.累计已读取的字节
				System.arraycopy(read, 0, allbyte, allbyte.length - bytesRead, bytesRead);
			}
			
			//【4】清空所有数据和状态，方便下次读写
			buf.clear();
			//【5】继续读取文件，写入buffer
			bytesRead = inChannel.read(buf);
		}
		System.out.println(new String(allbyte));
		aFile.close();
	}
}
