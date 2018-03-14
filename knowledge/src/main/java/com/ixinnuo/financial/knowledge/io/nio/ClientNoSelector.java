package com.ixinnuo.financial.knowledge.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class ClientNoSelector {

	public static void main(String[] args) throws Exception {
		// 创建一个套接字通道，注意这里必须使用无参形式
		SocketChannel channel = SocketChannel.open();
		// 设置为非阻塞模式，这个方法必须在实际连接之前调用(所以open的时候不能提供服务器地址，否则会自动连接)
		channel.configureBlocking(false);
		// 连接服务器，由于是非阻塞模式，这个方法会发起连接请求，并直接返回false(阻塞模式是一直等到链接成功并返回是否成功)
		channel.connect(new InetSocketAddress("localhost", 12345));
		// 获取发生了关注事件的Key集合，每个SelectionKey对应了注册的一个通道
		// OP_CONNECT 两种情况，链接成功或失败这个方法都会返回true
		// 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
		// 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
		while (!channel.finishConnect()) {
			// 在连接之前可以做点什么..
			System.out.print(".");
		}
		System.out.println("当前客户端" + channel.getLocalAddress());
		ByteBuffer buf = ByteBuffer.allocate(1024);
		Server.dowrite(channel, buf, "ccc");
		buf.clear();
		TimeUnit.SECONDS.sleep(2);
		String read = Server.read(channel, buf);
		System.out.println("收到响应：" + read);
		
			
		
	}
}
