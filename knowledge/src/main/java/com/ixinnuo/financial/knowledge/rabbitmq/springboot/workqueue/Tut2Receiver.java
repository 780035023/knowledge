package com.ixinnuo.financial.knowledge.rabbitmq.springboot.workqueue;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StopWatch;

import com.rabbitmq.client.Channel;


/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class Tut2Receiver {

    private final int instance;

    public Tut2Receiver(int i) {
        this.instance = i;
    }

    @RabbitListener(queues = "tut.hello")
    public void receive(String in,Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws InterruptedException, IOException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance + " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + this.instance + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
        channel.basicAck(tag, false);
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}