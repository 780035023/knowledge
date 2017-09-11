package com.ixinnuo.financial.knowledge.rabbitmq.springboot.hello;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author Gary Russell
 * @author Scott Deeg
 * @author Wayne Lund
 */
public class Tut1Receiver {

    @RabbitListener(queues = "hello")
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }

}