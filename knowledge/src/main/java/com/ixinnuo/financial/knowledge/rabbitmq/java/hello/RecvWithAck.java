package com.ixinnuo.financial.knowledge.rabbitmq.java.hello;

import java.io.IOException;

import com.ixinnuo.financial.knowledge.rabbitmq.java.ConnectionFactoryUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RecvWithAck {

    private final static String QUEUE_NAME = "helloAck";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                //显示确认消息收到，false，单个确认，ture批量
                channel.basicAck(envelope.getDeliveryTag(), false);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        /*
         * autoAck true 自动确认，服务器发送完了，自动认为完成，false 需要消费者明确的确认消息消费
         */
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}