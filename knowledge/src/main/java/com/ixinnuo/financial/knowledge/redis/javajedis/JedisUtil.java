package com.ixinnuo.financial.knowledge.redis.javajedis;

import redis.clients.jedis.Jedis;

public class JedisUtil {

    
    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.16.16.33",6379);
        jedis.auth("liqq123456");
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        System.out.println(value);
        jedis.close();
    }
    
    public static Jedis getJedis(){
        Jedis jedis = new Jedis("172.16.16.33",6379);
        jedis.auth("liqq123456");
        return jedis;
    }
    
    
}
