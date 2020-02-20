package cn.zhang.com.util;

import cn.zhang.com.dto.MessageDTO;
import cn.zhang.com.enums.WebSoketStatEnum;
import cn.zhang.com.model.User;

public interface WebSoketUtil  {
    //根据所给id向用户发送消息
    public void senmessageUser(Integer UserID,Integer friendID,String data);
    /*封装客户端接收端信息*/
    public MessageDTO sendMessageBean(User user, User duser, String data, Integer stat);
}
