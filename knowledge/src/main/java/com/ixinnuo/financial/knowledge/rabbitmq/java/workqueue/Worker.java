package com.ixinnuo.financial.knowledge.rabbitmq.java.workqueue;

import java.io.IOException;

import com.ixinnuo.financial.knowledge.rabbitmq.java.ConnectionFactoryUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = ConnectionFactoryUtil.getFactory();
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        /*
         * 当消息进入队列时，RabbitMQ只会分派消息。 它不看消费者的未确认消息的数量。 它只是盲目地向第n个消费者发送每个第n个消息。
         * 导致有的消费者很忙碌，有的消费者很空闲，为了避免这种情况，使用basicQos方法与prefetchCount = 1来控制流量
         * 这告诉RabbitMQ不要一次给一个工作者多个消息。 或者换句话说，在处理并确认前一个消息之前，不要向工作人员发送新消息。 
         * 它将发送到下一个还不忙的工作。
         */
        channel.basicQos(1);

        final Consumer consumer = new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                }
                finally {
                    System.out.println(" [x] Done");
                    /*
                     * 只确认当前消息已成功消费
                     */
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        /*
         * 需要显示的确认消息
         */
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
