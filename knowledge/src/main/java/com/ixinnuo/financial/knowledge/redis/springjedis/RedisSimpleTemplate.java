package com.ixinnuo.financial.knowledge.redis.springjedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

/**
 * 关于如何使用 RedisTemplate操作redis，建议查阅官方文档，见下面的地址：
 * http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#
 * redisTemplate.opsForValue();//操作字符串 redisTemplate.opsForHash();//操作hash
 * redisTemplate.opsForList();//操作list redisTemplate.opsForSet();//操作set
 * redisTemplate.opsForZSet();//操作有序set
 */
@Component("redisSimpleTemplate")
public class RedisSimpleTemplate {

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * 设置string
     * @param key
     *            must not be null.
     * @param value
     *            value must not be null.
     * @param expiration
     *            Defaulted to Expiration.persistent().option can be null.
     * @param setOption
     *            Defaulted to SetOption.UPSERT.
     */
    public void setString(String key, String value, Expiration expiration, SetOption setOption) {
        redisTemplate.execute(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                // Can cast to StringRedisConnection if using a
                // StringRedisTemplate
                ((StringRedisConnection) connection).set(key, value, expiration, setOption);
                Long size = connection.dbSize();
                return size;
            }
        });
    }

}
