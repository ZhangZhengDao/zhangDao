package cn.zhang.com;

import cn.zhang.com.dto.RedisD;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class ZhangApplicationTests {

  /*  @Test
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost",6379);
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }*/
    @Test
    public static void main(String[] args) {
       Jedis jedis= RedisD.getRedis();
       jedis.set("a","fe");
        System.out.println(jedis.get("a"));

    }

}
