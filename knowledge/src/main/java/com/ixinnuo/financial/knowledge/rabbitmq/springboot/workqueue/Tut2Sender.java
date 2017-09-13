package com.ixinnuo.financial.knowledge.rabbitmq.springboot.workqueue;

import javax.annotation.Resource;

/* Copyright 2015 the original author or authors. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. */

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Gary Russell
 * @author Scott Deeg
 */
public class Tut2Sender {

    @Autowired
    private RabbitTemplate template;

    @Resource(name="tut2Queue")
    private Queue queue;


    public void send(int dots) {
        StringBuilder builder = new StringBuilder("Hello");
        for (int i = 0; i < dots; i++) {
            builder.append('.');
        }
        builder.append(Integer.toString(dots));
        String message = builder.toString();
        template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }

}