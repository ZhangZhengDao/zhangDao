package cn.zhang.com.util.Imp;

import cn.zhang.com.config.WebSocket;
import cn.zhang.com.dto.MessageDTO;
import cn.zhang.com.dto.RedisD;
import cn.zhang.com.enums.WebSoketStatEnum;
import cn.zhang.com.model.User;
import cn.zhang.com.util.UserUtil;
import cn.zhang.com.util.WebSoketUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class WebSOketUtilImp implements WebSoketUtil {
    @Autowired
    private RedisD redisD;
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private WebSocket webSocket;

    @Override
    public void senmessageUser(Integer userID, Integer friendID, MessageDTO messageDTO) {
        User user = userUtil.IDfidUser(userID.toString());
        if (user == null) {
            return;
        }
        WebSoketStatEnum webSoketStatEnum = friendSndStat(userID, friendID);
        User friendUser = userUtil.IDfidUser(friendID.toString());
        //拿到用户的 websok 端口
        Jedis jedis = RedisD.getRedis();
        Map<String, String> map = jedis.hgetAll(friendUser.getAccount());
        JSONObject jsonObject=new JSONObject();
        redisD.setJilu(user, friendUser.getAccount(), messageDTO.getText(),messageDTO.getData());
        //根据状态判断发送什么样的消息类型
        if (StringUtils.equals(webSoketStatEnum.getDescribe(), WebSoketStatEnum.WEBSOKETSTAT_FALSE.getDescribe())) {
            //消息接收者不在线，添加聊天记录和未读消息
            redisD.AddfriendNewest(friendUser.getAccount(),messageDTO.getText(),user,messageDTO.getData());
            return;
        } else if (StringUtils.equals(webSoketStatEnum.getDescribe(), WebSoketStatEnum.WEBSOKETSTAT_TRUE.getDescribe())) {
            //在线，但未打开聊天窗口
            //向消息接收者发送实时消息
            messageDTO = sendMessageBean(user, friendUser, messageDTO.getText(),webSoketStatEnum.getStat(),messageDTO.getData());
            redisD.AddfriendNewest(friendUser.getAccount(),messageDTO.getText(),user,messageDTO.getData());
            jsonObject.put("message",messageDTO);
            try {
                webSocket.sedUser(map.get("id"),jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else if (StringUtils.equals(webSoketStatEnum.getDescribe(), WebSoketStatEnum.WEBSOKETSTAT_MASG.getDescribe())) {
            //在线打开了聊天敞口但不是当前用户
            //向消息接收者发送实时消息
            messageDTO = sendMessageBean(user, friendUser, messageDTO.getText(),webSoketStatEnum.getStat(),messageDTO.getData());
            redisD.AddfriendNewest(friendUser.getAccount(),messageDTO.getText(),user,messageDTO.getData());
            jsonObject.put("message",messageDTO);
            try {
                webSocket.sedUser(map.get("id"),jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        } else if (StringUtils.equals(webSoketStatEnum.getDescribe(), WebSoketStatEnum.WEBSOKETSTAT_MASGTRUE.getDescribe())) {
            //两个用户都打开了对方的窗口
            //直接向接受者发送消息
            messageDTO=sendMessageBean(user,friendUser,messageDTO.getText(),webSoketStatEnum.getStat(),messageDTO.getData());
            jsonObject.put("message",messageDTO);
            try {
                webSocket.sedUser(map.get("id"),jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    @Override
    public MessageDTO sendMessageBean(User user, User duser, String text,Integer stat,String data) {
        MessageDTO messageDTO=new MessageDTO();
        messageDTO.setText(text);
        messageDTO.setdUrl(duser.getAvatarUrl());
        messageDTO.setdName(duser.getName());
        messageDTO.setdAccount(duser.getAccount());
        messageDTO.setUserAccount(user.getAccount());
        messageDTO.setUserName(user.getName());
        messageDTO.setUserUrl(user.getAvatarUrl());
        messageDTO.setStat(stat);
        messageDTO.setData(data);
        return messageDTO;
    }


    /*判断消息接受用户的状态*/
    private WebSoketStatEnum friendSndStat(Integer userID, Integer friendID) {
        User friendUser = userUtil.IDfidUser(friendID.toString());
        if (friendUser == null) {
            return null;
        }
        Jedis redis = RedisD.getRedis();
        Boolean aBoolean = redis.exists(friendUser.getAccount());
        if (aBoolean) {
            //用户在线,判断聊天对象是否是该用户
            Map<String, String> map = redis.hgetAll(friendUser.getAccount());
            if (!map.get("stat").equals(0)) {
                //用户打开了聊天敞口,判断窗口对象是否是当前用户
                User user = userUtil.IDfidUser(userID.toString());
                if (map.get("stat").equals(user.getAccount())) {
                    //用户打开的窗口一致
                    return WebSoketStatEnum.WEBSOKETSTAT_MASGTRUE;
                }
                return WebSoketStatEnum.WEBSOKETSTAT_MASG;
            }
            return WebSoketStatEnum.WEBSOKETSTAT_TRUE;
        } else {
            //不在线
            return WebSoketStatEnum.WEBSOKETSTAT_FALSE;
        }
    }

}
