package com.ixinnuo.financial.knowledge.redis.springjedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 关于如何使用 RedisTemplate操作redis，建议查阅官方文档，见下面的地址：
 * http://docs.spring.io/spring-data/redis/docs/1.8.1.RELEASE/reference/html/#
 * redis:template
 * 
 * @author zhangkm
 */
@Component("redisTemplageDao")
public class RedisTemplateDao {

    /**
     * redisTemplate
     */
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    /**
     * 向一个String写入值，如果不存在，则创建一个String
     * 
     * @param key
     *            key
     * @param seconds
     *            持续时间（秒）
     * @param value
     *            value
     * @return 是否写成功
     */
    public boolean writeStringValue(final String key, final long seconds, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer =
                        redisTemplate.getStringSerializer();
                connection.setEx(serializer.serialize(key), seconds,
                        serializer.serialize(value));
                return true;
            }
        });
    }

    /**
     * 获取String的值
     * 
     * @param key
     *            key
     * @return value
     */
    public String getStringValue(String key) {
        // TODO: 这个方法似乎不是官方推荐方案，建议修改。
        String ret = (String) redisTemplate.opsForValue().get(key);
        return ret;
    }

    /**
     * 向有序列表插入一条数据，如果存在则覆盖
     * 
     * @param key
     *            key
     * @param score
     *            score
     * @param member
     *            member
     * @return 是否成功
     */
    public boolean addSortedSetMember(final String key, final double score, final String member) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer =
                        redisTemplate.getStringSerializer();
                connection.zAdd(serializer.serialize(key), score,
                        serializer.serialize(member));
                return true;
            }
        });
    }

    /**
     * 删除一个String/Set/Hash/List
     * 
     * @param key
     *            key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }
    

}
