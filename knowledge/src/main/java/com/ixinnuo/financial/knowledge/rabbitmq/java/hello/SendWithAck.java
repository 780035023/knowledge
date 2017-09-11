package com.ixinnuo.financial.knowledge.rabbitmq.java.hello;

import com.ixinnuo.financial.knowledge.rabbitmq.java.ConnectionFactoryUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendWithAck {

    private final static String QUEUE_NAME = "helloAck";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 直连交换机
        /* durable：true、false true：在服务器重启时，能够存活 exclusive
         * ：是否为当前连接的专用队列，在连接断开后，会自动删除该队列，生产环境中应该很少用到吧。
         * autodelete：当没有任何消费者使用时，自动删除该队列。this means that the queue will be
         * deleted when there are no more processes consuming messages from it. */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World with ack!";
        // 发送之前开启确认模式
        channel.confirmSelect();
        int size = 100;
        int i = 0;
        while (i<size) {
            Thread.sleep(1000);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + i + "'");
            i++;
        }
        channel.waitForConfirms(1000 * 5);
        channel.close();
        connection.close();
    }
}
