package com.ixinnuo.financial.knowledge.rabbitmq.springboot.workqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
@Configuration
@Profile("no_start")
public class Tut2Config {

    @Bean(name="tut2Queue")
    public Queue hello() {
        return new Queue("tut.hello");
    }

    private static class ReceiverConfig {

        @Bean
        public Tut2Receiver receiver1() {
            return new Tut2Receiver(1);
        }

        @Bean
        public Tut2Receiver receiver2() {
            return new Tut2Receiver(2);
        }

    }

    @Bean
    public Tut2Sender sender() {
        return new Tut2Sender();
    }

}
