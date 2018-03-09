package com.ixinnuo.financial.knowledge.io.bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;
/**
 * 客户端发送一个随机数，服务端接收+1，返回给客户端
 * @author 2476056494@qq.com
 *
 */
public class Server {

	private static int port = 12345;
	private static ExecutorService newThreadPool = BBNewThreadPool.newThreadPool(100);

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(port);
		System.out.println(ss.getLocalSocketAddress() + "服务启动...");
		while (true) {
			Socket socket = ss.accept();
			newThreadPool.submit(new ServerHandle(socket));
		}
	}

	static class ServerHandle implements Runnable {
		private Socket socket;

		public ServerHandle(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				//获取输入流
				InputStream inputStream = socket.getInputStream();
				//接收
				int read = inputStream.read();
				System.out.println(" 接收: " + socket.getRemoteSocketAddress() +" : "+ read);
				//获取输出流
				OutputStream outputStream = socket.getOutputStream();
				//模拟业务占用1秒
				Thread.sleep(1000);
				//发送
				outputStream.write(++read);
				outputStream.flush();
				System.out.println(" 发送: " + socket.getRemoteSocketAddress() +" : "+ read);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
