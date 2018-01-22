package com.ixinnuo.financial.knowledge.mina;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeServerHandler extends IoHandlerAdapter {
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

	/**
	 * 收到消息之后执行
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String str = message.toString();
		System.out.println("server Message Received..." + str);
		if (str.trim().equalsIgnoreCase("quit")) {
			session.closeOnFlush();
			return;
		}
		//模拟服务占用10秒
		TimeUnit.SECONDS.sleep(10);
		//这里是给客户端发送一个日期
		Date date = new Date();
		session.write(date.toString());
		System.out.println("server Message sent..." + date.toString());

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println(session.getId() + "IDLE " + session.getIdleCount(status));
	}
}