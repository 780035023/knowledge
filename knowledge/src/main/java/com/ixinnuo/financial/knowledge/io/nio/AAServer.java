package com.ixinnuo.financial.knowledge.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class AAServer {
	static private int PORT = 8000;
	static private int BACKLOG = 1024;

	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().setReuseAddress(true);
		ssc.socket().bind(new InetSocketAddress(PORT), BACKLOG);
		ssc.configureBlocking(false);

		Selector sel = Selector.open();
		ssc.register(sel, SelectionKey.OP_ACCEPT);
		// 阻塞，直到注册的channel有io要处理
		sel.select();
		Iterator i = sel.selectedKeys().iterator();
		// 遍历
		while (true) {
			while (i.hasNext()) {
				SelectionKey sk = (SelectionKey) i.next();
				i.remove();
				if (sk.isAcceptable()) {
					SelectableChannel channel = sk.channel();
					ServerSocketChannel ssChannel = (ServerSocketChannel) channel;
					SocketChannel sc = ssChannel.accept();
					if (sc == null) {
						continue;
					}
					sc.register(sel, SelectionKey.OP_READ);
				}
				if (sk.isReadable()) {
					SocketChannel sc = (SocketChannel) sk.channel();
					ByteBuffer allocate = ByteBuffer.allocate(BACKLOG);
					sc.read(allocate);
					allocate.flip();
					byte[] read = new byte[BACKLOG];
					int postion = 0;
					while (allocate.hasRemaining()) {
						byte b = allocate.get();
						read[postion] = b;
						b++;
					}
					System.out.println("read:" + new String(read));
					//关注写事件
					sk.interestOps(SelectionKey.OP_WRITE);
				}
				if(sk.isWritable()){
					SocketChannel sc = (SocketChannel) sk.channel();
					ByteBuffer allocate = ByteBuffer.allocate(BACKLOG);
					allocate.put((byte)11);
					sc.write(allocate);
				}
			}
		}
	}
}
