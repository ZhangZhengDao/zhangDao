package cn.zhang.com.config;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket/{name}")
public class WebSocket {


    private Session session;
    private String name;
    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(@PathParam("name") String name, Session session) {
        this.session = session;
        this.name = name;
        Jedis jedis = RedisD.getRedis();
        Boolean aBoolean = jedis.exists(name);
        System.out.println(name + "{{{{{{{{{{{{{{{{{{{{{{");
        if (aBoolean == false) {
            if (!name.equals("undefined")) {
                //给他一个状态，信息列表未被打开
                Map<String, String> map = new HashMap<>();
                map.put("stat", 0 + "");
                map.put("name", name);
                map.put("id", session.getId());
                jedis.hmset(name, map);
                //加入链接
                System.out.println("加入了链接");
                webSockets.add(this);
                System.out.println(webSockets.size());
            }
        }
    }

    /*关闭链接*/
    @OnClose
    public void onClose() {
        Jedis jedis = RedisD.getRedis();
        Boolean exists = jedis.exists(name);
        if (exists == true) {
            jedis.del(name);
            System.out.println("关闭了链接"+name);
            webSockets.remove(this);
        }
    }

    /*客户端向服务器发送消息后会调用该方法*/
    @OnMessage
    public void onMessage(String message) throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(message);
    }


    /*向每隔客户都发送数据*/
    public void send(String message) {
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*向指定客户端发送消息*/
    public void sedUser(String id, JSONObject jsonObject) throws IOException {
        for (WebSocket webSocket : webSockets) {
            if (webSocket.session.getId().equals(id)) {
                //当前用户在线，向他发送消息
                webSocket.session.getBasicRemote().sendText(jsonObject.toJSONString());
            }
        }
    }

    public int getsize() {
        return webSockets.size();
    }

    /*判断客户端是否打开了通信界面*/
    public String aBooleanCommunication(String name) {
        String[] split = name.split(":");
        if (split.length == 2) {
            return split[0];
        }
        return null;
    }

}

