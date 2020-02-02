package cn.zhang.com.dto;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisD {
    public static Jedis getRedis() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost", 6379);
       /* jedis.auth("214834");*/
        return jedis;
    }
}
