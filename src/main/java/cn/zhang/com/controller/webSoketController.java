package cn.zhang.com.controller;

import cn.zhang.com.config.WebSocket;
import cn.zhang.com.dto.MessageDTO;
import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.util.Imp.UserutilImp;
import cn.zhang.com.util.WebSoketUtil;
import cn.zhang.com.util.friendUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class webSoketController {

    @Autowired
    private WebSocket webSocket;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserutilImp userutilImp;
    @Autowired
    private WebSoketUtil webSoketUtil;
    @Autowired
    private friendUtil friendUtil;

    //收到信息，向接收端发送信息
    @ResponseBody
    @PostMapping("/jieshou")
    public Object jieshou(@RequestBody MessageDTO messageDTO, HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        String dAccount = messageDTO.getdAccount();
        System.out.println(messageDTO.getData());
        User friendUser = userutilImp.AccountfidUser(dAccount);
        if (user == null) {
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        //判断他们是否是好友
        if (!friendUtil.friendboolean(user.getId(), friendUser.getId())) {
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_Friend);
        }
        webSoketUtil.senmessageUser(user.getId(), friendUser.getId(),messageDTO);
        //给发送端的数据
        return "cengg";
    }

    //记录当前消息窗口的状态
    @PostMapping("/KUstat")
    @ResponseBody
    private String kustat(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String stat = jsonObject.get("static").toString();
        Jedis jedis = RedisD.getRedis();
        //拿到当前用户信息，记录打开消息窗口
        User user = (User) request.getSession().getAttribute("user");
        Boolean exists = jedis.exists(user.getAccount());
        if (exists) {
            //用户打开了聊天窗口 更改聊天对象
            List<String> id = jedis.hmget(user.getAccount(), "id");
            jedis.del(user.getAccount());
            Map<String, String> map = new HashMap<>();
            map.put("stat", stat + "");//记录了当前 用户打开了和谁聊天的窗口
            map.put("id", id.get(0));
            map.put("name", user.getAccount());
            jedis.hmset(user.getAccount(), map);
            /*清除这条未读记录*/
            new RedisD().reomnewest(jedis, user.getAccount(), stat);
        }
        //证明用户，打开了聊天窗口，拿到他和接收者的所有聊天记录
        if (!stat.equals("0")) {
            JSONObject jsonObject1 = new JSONObject();
            UserExample example = new UserExample();
            example.createCriteria().andAccountEqualTo(stat);
            jsonObject1.put("daccount", userMapper.selectByExample(example).get(0));
            if (new RedisD().panduan(jedis, user, stat) != null) {
                //证明有聊天记录
                String account = new RedisD().panduan(jedis, user, stat);
                List<String> lrange = jedis.lrange(account, 0, jedis.llen(account));
                //返回结果
                jsonObject1.put("user", account);
                jsonObject1.put("list", lrange);
                //将用户头像信息页返回
                jsonObject1.put("account", user.getAvatarUrl());
                return jsonObject1.toJSONString();
            } else {
                return jsonObject1.toJSONString();
            }
        }
        return "";
    }
}
