package com.ixinnuo.financial.knowledge.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;


public class SocketClient {

	private static SocketClient socketClient = new SocketClient();
	private static Socket socket = null;

	private SocketClient() {

		// 1.建立客户端socket连接，指定服务器位置及端口
		// try {
		// InetAddress remoteAddr = InetAddress.getByName(xtyhServer[0]);
		// InetAddress localAddr = InetAddress.getByName("localhost");
		// socket = new Socket(remoteAddr,
		// Integer.valueOf(xtyhServer[1]),localAddr,8888);
		// socket = new Socket(xtyhServer[0],
		// Integer.valueOf(xtyhServer[1]));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static SocketClient getInstance() {
		if (null == socketClient) {
			socketClient = new SocketClient();
		}
		return socketClient;
	}

	public static void main(String[] args) {
		new SocketClient().sentMsg("1234567890");
	}

	/**
	 * 给邢台银行socket服务发送消息，并实时获取响应结果
	 * 
	 * @param msg
	 * @return
	 */
	public String sentMsg(String msg) {
		try {
			String property = "localhost:8888";//PropertyUtil.getProperty("xtyhServer");
			System.out.println("socket服务器地址：" + property);
			String[] xtyhServer = property.split(":");
			socket = new Socket(xtyhServer[0], Integer.valueOf(xtyhServer[1]));
			// 2.得到socket读写流
			// 写出流
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			// 读入流
			InputStream is = socket.getInputStream();
			// 3.利用流按照一定的操作，对socket进行读写操作
			String clientAddres = socket.getLocalSocketAddress().toString();
			System.out.println(clientAddres + "发送服务器的数据信息：" + msg);
			// 加密操作
			msg = buwei(msg);
			pw.write(msg);
			pw.flush();
			socket.shutdownOutput();
			// 接收服务器的相应
			byte[] allByte = new byte[0];
			byte[] bufferByte = new byte[1024];
			int read = 0;
			while (0 < (read = is.read(bufferByte))) {
				System.out.println("读取内容字节长度：" + read);
				// 1.扩展总数组长度
				allByte = Arrays.copyOf(allByte, allByte.length + read);
				System.out.println("扩容后的总长度：" + allByte.length);
				// 2.合并读取的内容到总数组
				System.arraycopy(bufferByte, 0, allByte, allByte.length - read, read);
			}
			socket.shutdownInput();
			String response = new String(allByte, "UTF-8");
			// 解密操作,截掉前8位
			response = response.substring(8);
			System.out.println(clientAddres + "接收服务器的数据信息：" + response);
			// 4.关闭资源
			is.close();
			pw.close();
			os.close();
			if (StringUtils.isNotBlank(response)) {
				return response;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 报文头8位长度
	 * 
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	String buwei(String msg) throws UnsupportedEncodingException {
		int length = msg.getBytes("UTF-8").length;
		String valueOf = String.valueOf(length);
		int length2 = valueOf.length();
		String top;
		switch (8 - length2) {
		case 1:
			top = "0";
			break;
		case 2:
			top = "00";
			break;
		case 3:
			top = "000";
			break;
		case 4:
			top = "0000";
			break;
		case 5:
			top = "00000";
			break;
		case 6:
			top = "000000";
			break;
		case 7:
			top = "0000000";
			break;

		default:
			top = "";
			break;
		}
		return top + valueOf + msg;
	}

}
