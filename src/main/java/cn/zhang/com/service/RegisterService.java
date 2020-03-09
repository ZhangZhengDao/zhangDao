package cn.zhang.com.service;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhangzheng
 * @date 2020/3/1 13:38
 */
@Service
public class RegisterService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 对用户名进行校验
     *
     * @parpm 用户名 被校验值
     */
    public boolean NameVerify(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 对密码进行校验
     *
     * @param password 被校验值
     */
    public Boolean PwdVerify(String password) {
        String check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,20}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

    /**
     * 像数据添加数据
     *
     * @param name     用户名
     * @param password 密码
     * @param head     头像地址
     */
    public ResultDTO Insert(String name, String password, String head) {
        //向数据库添加涉及到了线程问题，比如有两个用户名一样的人，同时向数据库写入,利用redis启用单线程模式
//        Jedis jedis = RedisD.getRedis();
        String uuid = UUID.randomUUID().toString();
       try {
          /*  jedis.setnx(uuid, "zhang");
            jedis.expire(uuid, 5);*/
            if (!NameVerify(name)) {
                return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAMECHONGGU);
            }
            if (!PwdVerify(password)) {
                return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAMEPASSOWDTYPE);
            }
            //此时当前状态就是单线程了
            User record = new User();
            record.setName(name);
            record.setFriend("");
            record.setAvatarUrl(head);
            record.setAccount(uuid);
            record.setToken(uuid);
            record.setGmtCreate(System.currentTimeMillis());
            record.setGmtModified(record.getGmtCreate());
            record.setPasswd(password);
            userMapper.insert(record);
            return ResultDTO.okof();
        } catch (Exception e) {
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_CHUCESHIBAI);
        } finally {
            /*if (jedis.exists(uuid)) {
                jedis.del(uuid);
            }*/
        }
    }
}
