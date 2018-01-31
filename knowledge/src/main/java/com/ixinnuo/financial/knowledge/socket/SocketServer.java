package com.ixinnuo.financial.knowledge.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


/**
 * 接收客户端一次消息并响应结果，然后关闭这一次的连接
 * 字节流方式读取消息
 * @author 2476056494@qq.com
 *
 */
public class SocketServer {

	public static void main(String[] args) throws IOException {
		new SocketServer().oneServer();
	}

	/**
	 * 启动服务
	 * 
	 * @throws IOException
	 */
	public void oneServer() throws IOException {
		String property = "localhost:8888";//PropertyUtil.getProperty("xtyhServer");
		String[] xtyhServer = property.split(":");
		ServerSocket server = new ServerSocket(Integer.valueOf(xtyhServer[1]));
		System.out.println("socket服务器启动成功....");
		while (true) {
			Socket socket = server.accept();
			HandleMsg handleMsg = new HandleMsg(socket);
			Thread t = new Thread(handleMsg);
			t.start();
		}
	}

	/**
	 * 处理连接的线程
	 * 
	 * @author 2476056494@qq.com
	 *
	 */
	public static class HandleMsg implements Runnable {
		Socket socket;

		public HandleMsg(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				StringBuffer result = new StringBuffer();
				String receive = null;
				while (null != (receive = in.readLine())) {
					result.append(receive);
				}
				String resultStr = result.toString();
				//resultStr = CipherUtil.decryptMsg(resultStr);
				System.out.println("收到客户端"+socket.getRemoteSocketAddress().toString()+"消息：" + resultStr);
				// 给客户端发送一个时间戳,可以根据消息的内容具体响应不同结果
				//模拟服务占用2秒
				TimeUnit.SECONDS.sleep(2);
				String response = System.currentTimeMillis() + "";
			
				System.out.println("响应客户端"+socket.getRemoteSocketAddress().toString()+"消息：" + response);
				//response = CipherUtil.encryptMsg(response);
				writer.write(response);
				writer.flush();
				writer.close();
				in.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}
}
