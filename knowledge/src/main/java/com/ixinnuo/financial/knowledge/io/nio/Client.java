package com.ixinnuo.financial.knowledge.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

	private static AtomicInteger count = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();
		// 创建一个套接字通道，注意这里必须使用无参形式
		SocketChannel channel = SocketChannel.open();
		// 设置为非阻塞模式，这个方法必须在实际连接之前调用(所以open的时候不能提供服务器地址，否则会自动连接)
		channel.configureBlocking(false);
		// 连接服务器，由于是非阻塞模式，这个方法会发起连接请求，并直接返回false(阻塞模式是一直等到链接成功并返回是否成功)
		channel.connect(new InetSocketAddress("localhost", 12345));
		// 注册关联链接状态
		channel.register(selector, SelectionKey.OP_CONNECT, ByteBuffer.allocate(1024));
		while (true) {
			count.incrementAndGet();
			System.out.println("selector 轮询次数：" + count.get());

			if (selector.selectNow() == 0) {
				Thread.sleep(10000);// 休息10秒
				continue;
			}
			// 获取发生了关注事件的Key集合，每个SelectionKey对应了注册的一个通道
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			SelectionKey key = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				// OP_CONNECT 两种情况，链接成功或失败这个方法都会返回true
				if (key.isConnectable()) {
					// 由于非阻塞模式，connect只管发起连接请求，finishConnect()方法会阻塞到链接结束并返回是否成功
					// 另外还有一个isConnectionPending()返回的是是否处于正在连接状态(还在三次握手中)
					while (!channel.finishConnect()) {
						// 在连接之前可以做点什么..
					}
					System.out.println("connect:" + key.hashCode() + ";客户端" + channel.getLocalAddress());
					ByteBuffer buf = (ByteBuffer) key.attachment();
					Server.dowrite(channel, buf, "ccc");
					buf.clear();
					// 处理完后必须吧OP_CONNECT关注去掉，改为关注OP_READ
					key.interestOps(SelectionKey.OP_READ);

				}
				// OP_READ 有数据可读
				if (key.isReadable()) {
					System.out.println("read:" + key.hashCode());
					SocketChannel readChannel = (SocketChannel) key.channel();
					// 得到附件，就是上面SocketChannel进行register的时候的第三个参数,可为随意Object
					ByteBuffer buf = (ByteBuffer) key.attachment();
					// 读数据
					System.out.println(readChannel.getRemoteAddress() + "接收内容：" + Server.read(channel, buf));
					// 改变自身关注事件，可以用位或操作|组合事件
					iterator.remove();
				}
			}
		}
	}
}
