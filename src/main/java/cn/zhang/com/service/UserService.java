package cn.zhang.com.service;

import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public User addUser(User user) {
        //向数据查询是否有该用户
        if(user.getAccount()==null){
            return null;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountEqualTo(user.getAccount());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            userMapper.insert(user);
        } else {
            User user1 = new User();
            user1.setAvatarUrl(user.getAvatarUrl());
            user1.setToken(user1.getToken());
            user1.setAccount(user.getAccount());
            user1.setName(user.getName());
            if (StringUtils.isEmpty(user1.getName())){
                return null;
            }
            UserExample example = new UserExample();
            example.createCriteria().andAccountEqualTo(user.getAccount());
            userMapper.updateByExampleSelective(user1, example);
        }
        userExample.createCriteria().andAccountEqualTo(user.getAccount());
        return userMapper.selectByExample(userExample).get(0);

    }

    /*判断普通用户用户名和密码是否正确*/
    public User Denglu(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(user.getName())
                .andPasswdEqualTo(user.getPasswd());
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }
}
