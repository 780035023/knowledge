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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * nio模式 http://blog.csdn.net/hj7jay/article/details/51322287
 * 
 * @author 2476056494@qq.com
 *
 */
public class Server {

	private static AtomicInteger count = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		Selector selector = Selector.open();

		ServerSocketChannel acceptChannel = ServerSocketChannel.open();
		acceptChannel.socket().bind(new InetSocketAddress(12345));
		acceptChannel.configureBlocking(false);
		// 此channel注册到selector的accept事件下
		acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("服务器启动12345...");
		// 轮询
		while (true) {
			count.incrementAndGet();
			System.out.println("selector 轮询次数：" + count.get());
			// 测试等待事件发生，分为直接返回的selectNow()和阻塞等待的select()，另外也可加一个参数表示阻塞超时
			// 停止阻塞的方法有两种: 中断线程和selector.wakeup()，有事件发生时，会自动的wakeup()
			// 方法返回为select出的事件数(参见后面的注释有说明这个值为什么可能为0).
			// 另外务必注意一个问题是，当selector被select()阻塞时，其他的线程调用同一个selector的register也会被阻塞到select返回为止
			// select操作会把发生关注事件的Key加入到selectionKeys中(只管加不管减)
			if (selector.select(10000) == 0) {
				continue;
			}
			// 获取发生了关注事件的Key集合，每个SelectionKey对应了注册的一个通道
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = keys.iterator();
			SelectionKey key = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				// OP_ACCEPT 这个只有ServerSocketChannel才有可能触发
				try {
					if (key.isAcceptable()) {
						System.out.println("accept:" + key.hashCode());
						// 得到与客户端的套接字通道
						SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
						System.out.println("接收客户端连接" + channel.getRemoteAddress());
						// 同样设置为非阻塞模式
						channel.configureBlocking(false);
						// 同样将于客户端的通道在selector上注册，OP_READ对应可读事件(对方有写入数据),可以通过key获取关联的选择器
						channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
						//iterator.remove();
					}
					// OP_READ 有数据可读
					if (key.isReadable()) {
						System.out.println("read:" + key.hashCode());
						SocketChannel channel = (SocketChannel) key.channel();
						// 得到附件，就是上面SocketChannel进行register的时候的第三个参数,可为随意Object
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						// 读数据
						String read = read(channel, buffer);
						System.out.println("接收客户端消息" + channel.getRemoteAddress() + read);
						// 响应数据
						dowrite(channel, buffer, "sss");
						selectedKeys.remove(key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

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
			if(bytesRead == 0){
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
			//继续读取
			bytesRead = channel.read(buffer);
		}
		return new String(allbyte, "utf-8");
	}

	public static void dowrite(SocketChannel channel, ByteBuffer buf, String msg) throws IOException {
		byte[] bytes = msg.getBytes("UTF-8");
		int capacity = buf.capacity();
		int offset=0;
		while(offset+capacity < bytes.length){
			buf.put(bytes, offset, capacity);
			channel.write(buf);
			offset = offset + capacity;
		}
		buf.put(bytes, offset, bytes.length - offset);
		channel.write(buf);
		System.out.println("发送消息" + channel.getRemoteAddress() + msg);
	}
}
