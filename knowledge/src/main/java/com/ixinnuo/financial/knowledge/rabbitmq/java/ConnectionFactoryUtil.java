package com.ixinnuo.financial.knowledge.rabbitmq.java;

import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryUtil {

    
    public static ConnectionFactory getFactory(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("172.16.16.33");
        factory.setPort(5672);// 默认5672
        factory.setUsername("liqq");
        factory.setPassword("liqq");
        return factory;
    }
}
