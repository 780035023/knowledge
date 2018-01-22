package com.ixinnuo.financial.knowledge.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter implements IoHandler{
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
		
		System.out.println(session.getId() + "client Message Received..." + str);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		int idleCount = session.getIdleCount(status);
		System.out.println("client IDLE " + idleCount);
	}
	
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		System.out.println(session.getId() + "client Message sent..." + message.toString());
	}
}