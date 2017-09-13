package com.ixinnuo.financial.knowledge.redis.javajedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

    public JedisPool pool;

    @Before
    public void getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(8);
        jedisPoolConfig.setMaxIdle(8);
        jedisPoolConfig.setMaxWaitMillis(-1);
        jedisPoolConfig.setMinIdle(0);
        JedisPool pool = new JedisPool(jedisPoolConfig, "172.16.16.33", 6379, 100, "liqq123456");
        this.pool = pool;
    }

    @Test
    public void testString() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // -----添加数据----------
            jedis.set("name", "xinxin");// 向key-->name中放入了value-->xinxin
            System.out.println(jedis.get("name"));// 执行结果：xinxin

            jedis.append("name", " is my lover"); // 拼接
            System.out.println(jedis.get("name"));

            jedis.del("name");  // 删除某个键
            System.out.println(jedis.get("name"));
            // 设置多个键值对
            jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
            jedis.incr("age"); // 进行加1操作
            System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }

    @Test
    public void testMap() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // -----添加数据----------
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "xinxin");
            map.put("age", "22");
            map.put("qq", "123456");
            jedis.hmset("user", map);
            // 取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
            // 第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
            List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
            System.out.println(rsmap);

            // 删除map中的某个键值
            jedis.hdel("user", "age");
            System.out.println(jedis.hmget("user", "age")); // 因为删除了，所以返回的是null
            System.out.println(jedis.hlen("user")); // 返回key为user的键中存放的值的个数2
            System.out.println(jedis.exists("user"));// 是否存在key为user的记录 返回true
            System.out.println(jedis.hkeys("user"));// 返回map对象中的所有key
            System.out.println(jedis.hvals("user"));// 返回map对象中的所有value

            Iterator<String> iter = jedis.hkeys("user").iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                System.out.println(key + ":" + jedis.hmget("user", key));
            }
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }

    @Test
    public void testList() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // 开始前，先移除所有的内容
            jedis.del("java framework");
            System.out.println(jedis.lrange("java framework", 0, -1));
            // 先向key java framework中存放三条数据,左为队首
            jedis.lpush("java framework", "spring");
            jedis.lpush("java framework", "struts");
            jedis.lpush("java framework", "hibernate");
            // 再取出所有数据jedis.lrange是按范围取出，
            // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
            System.out.println(jedis.lrange("java framework", 0, -1));

            jedis.del("java framework");
            // 右为队尾
            jedis.rpush("java framework", "spring");
            jedis.rpush("java framework", "struts");
            jedis.rpush("java framework", "hibernate");
            System.out.println(jedis.lrange("java framework", 0, -1));
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }

    @Test
    public void testSet() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // 先删除key
            jedis.del("user1");
            // 添加
            jedis.sadd("user1", "liuling");
            jedis.sadd("user1", "xinxin");
            jedis.sadd("user1", "ling");
            jedis.sadd("user1", "zhangxinxin");
            jedis.sadd("user1", "who");
            // 移除noname
            jedis.srem("user1", "who");
            System.out.println(jedis.smembers("user1"));// 获取所有加入的value
            System.out.println(jedis.sismember("user1", "who"));// 判断 who
                                                                // 是否是user集合的元素
            System.out.println(jedis.srandmember("user1"));// 随机返回集合中一个值，不删除
            System.out.println(jedis.scard("user1"));// 返回集合的元素个数
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }

    @Test
    public void testSort() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            // jedis 排序
            // 注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
            jedis.del("a");// 先清除数据，再加入数据进行测试
            jedis.rpush("a", "1");
            jedis.lpush("a", "6");
            jedis.lpush("a", "3");
            jedis.lpush("a", "9");
            System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
            System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //输入排序后结果,不影响原来顺序
            System.out.println(jedis.lrange("a", 0, -1));
        }
        finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        pool.destroy();
    }
}
