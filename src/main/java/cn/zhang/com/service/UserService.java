package cn.zhang.com.service;

import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private Usermapper usermapper;
    public  void addUser(User user) {
        //向数据查询是否有该用户
        User byAccount = usermapper.findByAccount(user.getAccount());
        if (byAccount==null){
            usermapper.insert(user);
        }else{
            usermapper.updateUser(user.getToken(),user.getGmt_create(),user.getGmt_modified(),user.getAccount());
        }
    }
}
