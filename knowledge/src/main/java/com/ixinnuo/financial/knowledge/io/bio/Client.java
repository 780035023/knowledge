package com.ixinnuo.financial.knowledge.io.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import com.ixinnuo.financial.knowledge.thread.concurrent.BBNewThreadPool;
/**
 * 客户端给服务端发送随机数，并接收服务端的响应，+1
 * @author 2476056494@qq.com
 *http://blog.csdn.net/anxpp/article/details/51512200
 */
public class Client implements Runnable {

	private int port = 12345;
	private String host = "localhost";

	@Override
	public void run() {
		Socket socket = null;
		try {
			//连接
			socket = new Socket(host, port);
			//获取输出流
			OutputStream outputStream = socket.getOutputStream();
			Random random = new Random();
			int nextInt = random.nextInt(100);
			//发送
			outputStream.write(nextInt);
			outputStream.flush();
			System.out.println(socket.getLocalSocketAddress() + " 发送: " + nextInt);
			//获取输入流
			InputStream inputStream = socket.getInputStream();
			//接收
			int read = inputStream.read();
			System.out.println(socket.getLocalSocketAddress() + " 接收: " + read);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws Exception {
		ExecutorService newThreadPool = BBNewThreadPool.newThreadPool(100);
		//提交10个任务
		for (int i = 0; i < 10; i++) {
			newThreadPool.submit(new Client());
		}
		newThreadPool.shutdown();
		while (!newThreadPool.isTerminated()) {
			Thread.sleep(1000);
		}
		System.out.println("任务结束");
	}

}
