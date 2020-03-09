package cn.zhang.com.controller;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.dto.UserFriendLISTDTO;
import cn.zhang.com.enums.NotificationEnum;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.mapper.NotiFicationMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.NotiFication;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.service.FriendService;
import cn.zhang.com.util.UserUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class friendController {

    @Autowired
    private NotiFicationMapper notiFicationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private RedisD redisD;
    /*向被加好友的用户发送验证消息*/
    @PostMapping("/addFriend")
    @ResponseBody
    private ResultDTO addFriend(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        //向数酷添加消息
        NotiFication notiFication=new NotiFication();
        notiFication.setType(NotificationEnum.RELY_FRIEND.getType());
        notiFication.setReceiver(Long.valueOf(jsonObject.get("userid").toString()));
        notiFication.setNotifier(user.getId().longValue());//发起人id
        notiFication.setStatus(0);
        notiFication.setGmtCreate(System.currentTimeMillis());
        notiFication.setOuterid(System.currentTimeMillis());//由于是添加好友类型，记录为时间
        notiFicationMapper.insert(notiFication);
        //跟新当前用户数据库内容
        return ResultDTO.okof();
    }

    //取消对此好友的关注
    @PostMapping("/reomFriend")
    @ResponseBody
    private ResultDTO reomFriend(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        String[] split = user.getFriend().split(",");
        String a="";
        for (String s : split) {
            if (!StringUtils.equals(s,jsonObject.get("userid"))){
                a=a+s+",";
            }
        }
        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        User record = new User();
        record.setFriend(a);
        userMapper.updateByExampleSelective(record, example);
        //更新 session里面的user信息
        User user1 = userMapper.selectByPrimaryKey(user.getId());
        request.getSession().setAttribute("user",user1);
        //清除以前聊天记录
        String userid = userUtil.IDfidUser(jsonObject.get("userid").toString()).getAccount();
        redisD.reomveChat(user.getAccount(), userid);
        redisD.reomnewest(RedisD.getRedis(),user1.getAccount(),userid);
        return ResultDTO.okof();
    }

    /*查询出当前用户所有的好友，标记出为关注他的用户*/
    @GetMapping("/friend")
    public String friend(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        List<UserFriendLISTDTO> userFriendLISTDTO=friendService.friendList(user);
        model.addAttribute("friendList",userFriendLISTDTO);
        return "friend";
    }

    /*查询出当前用户所有的好友，标记出为关注他的用户*/
    @GetMapping("/chat")
    public String index1(HttpServletRequest request, Model model){
        User user = (User) request.getSession().getAttribute("user");
        List<UserFriendLISTDTO> userFriendLISTDTO=friendService.friendList(user);
        Jedis jedis= RedisD.getRedis();
        /*最新消息*/
        model.addAttribute("friendTextDTO", new RedisD().getUserLike(jedis,user,userMapper));
        /*好友列表*/
        model.addAttribute("friendList",userFriendLISTDTO);
        return "chat";
    }

}
