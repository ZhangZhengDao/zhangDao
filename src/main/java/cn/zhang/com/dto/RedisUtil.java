package cn.zhang.com.dto;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component //注入spring容器
    public class RedisUtil {
        @Resource
        private RedisTemplate<String, Object> redisTemplate;
        /**
         * 普通缓存获取
         * @param key 键
         * @return 值
         */
        public Object get(String key) {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        }
    }

