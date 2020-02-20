package cn.zhang.com.util.Imp;

import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.util.UserUtil;
import cn.zhang.com.util.friendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class friendUtilImp implements friendUtil {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserUtil userUtil;

    @Override
    public boolean friendboolean(Integer userid, Integer friendid) {
        User user = userMapper.selectByPrimaryKey(userid);
        String[] split = user.getFriend().split(",");
        for (String s : split) {
            if (StringUtils.equals(s, friendid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addfriend(Integer userID, Integer friendID) {
        boolean friendboolean = friendboolean(userID, friendID);
        boolean friendboolean1 = friendboolean(friendID, userID);
        if (friendboolean && friendboolean1 == false) {
            addfrienduser(friendID, userID);
        }
        if (friendboolean1 && friendboolean == false) {
            addfrienduser(userID, friendID);
        }
        if (friendboolean == true && friendboolean1 == true) {
            return true;
        }
        if (friendboolean == false && friendboolean1 == false) {
            addfrienduser(friendID, userID);
            addfrienduser(userID, friendID);
        }
        return false;
    }

    /*添加好友*/
    private void addfrienduser(Integer userID, Integer friendID) {
        User user = userMapper.selectByPrimaryKey(userID);
        user.setFriend(friendID + ",");
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user, example);
    }
}
