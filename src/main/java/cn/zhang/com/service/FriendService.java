package cn.zhang.com.service;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.UserFriendLISTDTO;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    @Autowired
    private UserMapper userMapper;

    public List<UserFriendLISTDTO> friendList(User user) {
        String[] split = user.getFriend().split(",");
        System.out.println(split.length);

        List<UserFriendLISTDTO> list = new ArrayList<>();
        if (split.length==0){
            return null;
        }
        if (split[0].isEmpty()){
            return null;
        }
        for (String s : split) {
            UserFriendLISTDTO userFriendLISTDTO = new UserFriendLISTDTO();
            User user1 = userMapper.selectByPrimaryKey(Integer.valueOf(s));
            String[] split1 = user1.getFriend().split(",");
            for (String s1 : split1) {
                if (StringUtils.equals(s1, user.getId())) {
                    //是否互相关注
                    userFriendLISTDTO.setOppositeUser(true);
                    break;
                }
            }
                //查询好友对象是否在线
                userFriendLISTDTO.setStat(new RedisD().UserStat(user1.getAccount()));
                userFriendLISTDTO.setUser(user1);
                list.add(userFriendLISTDTO);
            }
        return list;
    }
}