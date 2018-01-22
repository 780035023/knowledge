package com.ixinnuo.financial.knowledge.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaTimeClient {
	private static final int PORT = 9123;

	public static void main(String[] args) throws IOException {
		NioSocketConnector connector = new NioSocketConnector();
        //设置编码解码器
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                .forName("UTF-8"))));
        //设置Handler
        TimeClientHandler timeClientHandler = new TimeClientHandler();
        connector.setHandler(timeClientHandler);

        //获取连接，该方法为异步执行
        ConnectFuture future = connector.connect(new InetSocketAddress("localhost",PORT));
        
        //等待连接建立
        future.awaitUninterruptibly();
        
        //获取session
        IoSession session = future.getSession();
        try {
        	session.write("hello 中国");
		} catch (Exception e) {
			e.printStackTrace();
		}
        //等待session关闭
        session.getCloseFuture().awaitUninterruptibly();
        
        //释放connector资源
        //实际开发中，不要频繁关闭connector，因为它是重量级资源，应该设计成单例或者连接池
        connector.dispose();
	}
}
