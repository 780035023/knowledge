package com.ixinnuo.financial.knowledge.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * nio模式 http://blog.csdn.net/hj7jay/article/details/51322287
 * 
 * @author 2476056494@qq.com
 *
 */
public class ServerNoSelector {

	public static void main(String[] args) throws Exception {

		ServerSocketChannel acceptChannel = ServerSocketChannel.open();
		acceptChannel.configureBlocking(false);
		acceptChannel.socket().bind(new InetSocketAddress(12345));
		// 此channel注册到selector的accept事件下
		System.out.println("服务器启动12345...");
		try {
			// 得到与客户端的套接字通道
			SocketChannel channel = acceptChannel.accept();
			while(null == channel){
				//等待连接
				channel = acceptChannel.accept();
				System.out.println("等待连接.");
				TimeUnit.SECONDS.sleep(2);
			}
			System.out.println("接收客户端连接" + channel.getRemoteAddress());
			// 同样设置为非阻塞模式
			channel.configureBlocking(false);
			// 同样将于客户端的通道在selector上注册，OP_READ对应可读事件(对方有写入数据),可以通过key获取关联的选择器
			// OP_READ 有数据可读
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			String read = read(channel, buffer);
			System.out.println("接收客户端消息" + channel.getRemoteAddress() + read);
			// 响应数据
			dowrite(channel, buffer, "sss");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读数据
	 * 
	 * @param channel
	 * @param buffer
	 * @return
	 * @throws IOException
	 */
	public static String read(SocketChannel channel, ByteBuffer buffer) throws IOException {
		// 所有字节
		byte[] allbyte = new byte[0];
		// 每次读取的字节
		byte[] read = null;
		// 【1】读取，内容--》channel--》buffer 写入buffer
		int bytesRead = channel.read(buffer);
		while (bytesRead != -1) {
			if (bytesRead == 0) {
				continue;
			}
			read = new byte[bytesRead];
			// 【2】切换模式
			buffer.flip();
			// 【3】buffer->out，读出buffer
			while (buffer.hasRemaining()) {
				buffer.get(read);
				// 1.扩展总长度
				allbyte = Arrays.copyOf(allbyte, allbyte.length + bytesRead);
				// 2.累计已读取的字节
				System.arraycopy(read, 0, allbyte, allbyte.length - bytesRead, bytesRead);
			}
			// 继续读取
			bytesRead = channel.read(buffer);
		}
		return new String(allbyte, "utf-8");
	}

	public static void dowrite(SocketChannel channel, ByteBuffer buf, String msg) throws IOException {
		byte[] bytes = msg.getBytes("UTF-8");
		int capacity = buf.capacity();
		int offset = 0;
		while (offset + capacity < bytes.length) {
			buf.put(bytes, offset, capacity);
			channel.write(buf);
			offset = offset + capacity;
		}
		buf.put(bytes, offset, bytes.length - offset);
		channel.write(buf);
		System.out.println("发送消息" + channel.getRemoteAddress() + msg);
	}
}
