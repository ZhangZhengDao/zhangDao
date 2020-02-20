package cn.zhang.com.dto;

import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.util.Imp.UserutilImp;
import cn.zhang.com.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;


@Service
public class RedisD {

    @Autowired
    public UserUtil userUtil;
    @Autowired
    public UserMapper userMapper;

    public static Jedis getRedis() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost", 6379);
        /* jedis.auth("214834");*/
        return jedis;
    }

    /*存储用户的聊天记录*/
    public void setJilu( User user, String account, String text) {
        Jedis jedis=RedisD.getRedis();
        //先判断他们 是不是已经有了 消息记录
        String aaa = panduan(jedis, user, account);
        if (aaa != null) {
            Integer c = Math.toIntExact(jedis.llen(aaa)) + 1;
            jedis.rpush(aaa, user.getAccount() + ":" + c + ":" + text);
        } else {
            //第一次来天 创建聊天记录就
            jedis.lpush(user.getAccount() + "," + account, user.getAccount() + ":" + 1 + ":" + text);
        }
    }

    //判断当前用户和接收者 以前是否有聊天记录
    public String panduan(Jedis jedis, User user, String account) {
        Boolean a = jedis.exists(user.getAccount() + "," + account);
        Boolean b = jedis.exists(account + "," + user.getAccount());
        if (a) {
            return user.getAccount() + "," + account;
        }
        if (b) {
            return account + "," + user.getAccount();
        }
        return null;
    }

    //判断当前用户是否在线
    public Boolean UserStat( String Account) {
        Boolean aBoolean = RedisD.getRedis().exists(Account);
        return aBoolean;
    }

    /*过去用户的聊天记录*/
    public friendTextDTO getText(Jedis jedis, String keys, User user, UserMapper userMapper) {
        //先判断用户是否有聊天记录
        List<String> lrange = jedis.lrange(keys, 0, jedis.llen(keys));
        friendTextDTO friendTextDTO = new friendTextDTO();
        String[] split1 = keys.split(",");
        String daccount = split1[0];
        if (!StringUtils.equals(user.getAccount(), split1[1])) {
            daccount = split1[1];
        }
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(daccount);
        List<User> users = userMapper.selectByExample(example);
        friendTextDTO.setUser(users.get(0));
        //拿到未读消息内容
        List<String> s = getnewest(user.getAccount(), users.get(0).getAccount(), jedis);
        if (s.size() == 2) {
            //证明有和这个用户的未读消息
            friendTextDTO.setNewestsize(Integer.valueOf(s.get(0)));
            friendTextDTO.setTextend(s.get(1));
        }
        return friendTextDTO;

    }

    //模糊查找关于当前用户的 所有聊天信息
    public List<friendTextDTO> getUserLike(Jedis jedis, User user, UserMapper userMapper) {
        Set<String> keys = jedis.keys("*" + user.getAccount() + "*");
        if (keys.size() == 0) {
            return null;
        }
        List<friendTextDTO> list = new ArrayList<>();
        for (String key : keys) {
            if (key.split(",").length == 2) {
                list.add(getText(jedis, key, user, userMapper));
            }
        }
        return list;

    }

    /*查询当前用户的所有未读消息*/
    public List<String> getnewest(String account, String daccount, Jedis jedis) {
        Map<String, String> map = jedis.hgetAll("newestMap" + account);
        List<String> list = new ArrayList<>();
        map.forEach((k, v) -> {
            if (k.equals(daccount)) {
                list.add(v);
                list.add(jedis.hmget("newestMap" + account, "text" + daccount).get(0));
            }
        });
        return list;
    }

    /*清除用户已读的未读消息*/
    public void reomnewest(Jedis jedis, String account, String daccount) {
        jedis.hdel("newestMap" + account, daccount, "text" + daccount);
    }

    /*向用户发送消息*/
    public void Userfriend(String account, String daccount) {

    }


    //清除用户聊天记录
    public void reomveChat(String account, String daccount) {
        User user = userUtil.AccountfidUser(account);
        String panduan = panduan(RedisD.getRedis(), user, daccount);
        if (panduan != null) {
            RedisD.getRedis().del(panduan);
        }

    }
    /*添加用户的未读消息*/
    public void AddfriendNewest(String daccount, String text, User user) {
        Jedis jedis=RedisD.getRedis();
        Boolean aBoolean = jedis.exists("newestMap" + daccount);
        if (aBoolean) {
            List<String> hmget = jedis.hmget("newestMap" + daccount, user.getAccount());
            if (hmget.size() != 0) {
                jedis.hincrBy("newestMap" + daccount, user.getAccount(), 1);
                jedis.hset("newestMap" + daccount, "text"+user.getAccount(), text);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("text"+user.getAccount(), text);
                map.put(user.getAccount(), 1 + "");
                jedis.hmset("newestMap" + daccount, map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(user.getAccount(), 1 + "");
            map.put("text"+user.getAccount(), text);
            jedis.hmset("newestMap" + daccount, map);
        }
    }

}
