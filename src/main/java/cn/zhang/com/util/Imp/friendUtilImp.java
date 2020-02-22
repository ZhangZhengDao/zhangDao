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
        User friendUser = userMapper.selectByPrimaryKey(friendid);
        boolean userFriend = false;
        boolean Friend = false;
        String[] split = user.getFriend().split(",");
        for (String s : split) {
            if (StringUtils.equals(s, friendid)) {
                userFriend = true;
            }
        }
        String[] split1 = friendUser.getFriend().split(",");
        for (String s : split1) {
            if (StringUtils.equals(s, userid)) {
                Friend = true;
            }
        }
        if (userFriend == true && Friend == true) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addfriend(Integer userID, Integer friendID) {
        boolean a = friendTwo(userID, friendID);
        boolean b = friendTwo(friendID, userID);
        if (b == true && a == true) {
            return true;
        }
        if (a == false && b == true) {
            addfrienduser(userID, friendID);
            return true;
        }
        if (a == true && b == false) {
            addfrienduser(friendID, userID);
            return true;
        }
        if (a == false && b == false) {
            addfrienduser(userID, friendID);
            addfrienduser(friendID, userID);
            return true;
        }
        return false;
    }

    /*添加好友*/
    private void addfrienduser(Integer userID, Integer friendID) {
        User user = userMapper.selectByPrimaryKey(userID);
        user.setFriend(user.getFriend()+friendID + ",");
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        userMapper.updateByExampleSelective(user, example);
    }

    /*
     * 判断当前用户好友列表是否存在对方
     * @return true or false
     * */
    private boolean friendTwo(Integer userID, Integer friendID) {
        User user = userUtil.IDfidUser(userID.toString());
        User userFriend = userUtil.IDfidUser(friendID.toString());
        String[] split = user.getFriend().split(",");
        for (String s : split) {
            if (StringUtils.equals(s, friendID)) {
                return true;
            }
        }
        return false;
    }
}
