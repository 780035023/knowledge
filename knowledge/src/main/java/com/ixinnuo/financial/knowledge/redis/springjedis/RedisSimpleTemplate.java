package com.ixinnuo.financial.knowledge.redis.springjedis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 关于如何使用 RedisTemplate操作redis，建议查阅官方文档，见下面的地址：
 * http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#
 * redisTemplate.opsForValue();//操作字符串
 * redisTemplate.opsForHash();//操作hash
 * redisTemplate.opsForList();//操作list 
 * redisTemplate.opsForSet();//操作set
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
     * 
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
                // 转化为String专用的模板
                ((StringRedisConnection) connection).set(key, value, expiration, setOption);
                Long size = connection.dbSize();
                return size;
            }
        });
    }

    /**
     * 获取key的String类型value
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
        return redisTemplate.execute(new RedisCallback<String>() {

            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                // 使用通用模板，需要序列化key，value
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] keyByte = serializer.serialize(key);
                String value = serializer.deserialize(connection.get(keyByte));
                return value;
            }
        });
    }

    /**
     * 设置hash
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Boolean setHash(String key, String field, String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                // 使用通用模板
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();

                boolean result = connection.hSet(serializer.serialize(key), serializer.serialize(field),
                        serializer.serialize(value));
                return result;
            }
        });
    }
    
    /**
     * 获取hash类型key的指定域的值
     * @param key
     * @param field
     * @return
     */
    public String getHash(String key, String field) {
        //使用操作hash的方法
        return (String) redisTemplate.opsForHash().get(key, field);
    }
    
    /**
     * 设置list类型
     * @param key
     * @param list
     * @return
     */
    public Long setList(String key, List list) {
        //使用操作hash的方法
        return redisTemplate.opsForList().leftPushAll(key, list);
    }
    /**
     * 获取list类型的范围，
     * @param key
     * @param start 起始，第一个为0，包含
     * @param end 结束，-1表示倒数第一个，包含
     * @return
     */
    public List<?> getList(String key,long start, long end) {
        //使用操作hash的方法
        return redisTemplate.opsForList().range(key, start, end);
    }

}
