package com.ixinnuo.financial.knowledge.rabbitmq.java.workqueue;

import com.ixinnuo.financial.knowledge.rabbitmq.java.ConnectionFactoryUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 工作队列，也就是任务队列，背后的主要思想是避免立即执行资源密集型任务，并且必须等待完成。
 * 这些任务在多个消费者之间是共享的，一个消息只有一份
 * 首先启动两个Woreker，然后再启动NewTask，
 * @author aisino
 *
 */
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        
        ConnectionFactory factory = ConnectionFactoryUtil.getFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        String[] argv2 = {"First message.","Second message..","Third message...","Fourth message....","Fifth message....."};
        for (int i = 0; i < argv2.length; i++) {
            String message = argv2[i];
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
