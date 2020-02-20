package cn.zhang.com.service;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.UserFriendLISTDTO;
import cn.zhang.com.dto.friendDTO;
import cn.zhang.com.dto.peopleDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.*;
import cn.zhang.com.util.Imp.UserutilImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class peopleService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired
    private UserutilImp userutilImp;
    @Autowired
    private FriendService friendService;


    /*
     * 计算用户个人信息
     * */
    public peopleDTO getPeople(Integer id, User user2) {
        //根据id拿到用户信息
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
        }
        peopleDTO peopledto = new peopleDTO();
        peopledto.setUser(user);
        //拿到所有问题数
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questions = questionMapper.selectByExample(example);
        peopledto.setWentishu(questions.size());
        //拿到阅读数
        Integer a = 0;
        Integer b = 0;
        for (Question question : questions) {
            a = a + question.getViewCount();
            b = b + question.getCommentCount();
        }
        peopledto.setYuedu(a);
        peopledto.setQhuifu(b);
        //拿到回复的点赞数
        CommentExample example1 = new CommentExample();
        example1.createCriteria().andCommentatorEqualTo(user.getId());
        List<Comment> comments = commentMapper.selectByExample(example1);
        //拿到评论数
        peopledto.setPinglunhuifu(comments.size());
        Integer c = 0;
        for (Comment comment : comments) {
            c = c + Math.toIntExact(comment.getLikeCount());
        }
        peopledto.setDinazan(c);
        //拿到所有问题
        peopledto.setQuestionList(questions);
        //拿到所有回复内容
        peopledto.setComments(comments);
        //用户年龄
        Long dat = System.currentTimeMillis() - user.getGmtModified();
        peopledto.setData(dat / (1000 * 3600 * 24) + "");
        //获取用户在线状态
        Jedis jedis = RedisD.getRedis();
        Boolean mykey = jedis.exists(user.getAccount());
        if (mykey) {
            peopledto.setStat(1 + "");
        }
        //查询出 他关注的好友
        String friend = user.getFriend();
        if (friend != null && !friend.equals("")) {
            String[] split = friend.split(",");
            List<friendDTO> list = new ArrayList<>();
            for (String s : split) {
                friendDTO friendDTO = new friendDTO();
                //向数据库查询到他关注的每一个好友并获取他们的状态
                User user1 = userMapper.selectByPrimaryKey(Integer.valueOf(s));
                friendDTO.setUser(user1);
                friendDTO.setStat(1 + "");
                //获取当前被关注用户的状态
                Boolean exists = jedis.exists(user1.getAccount());
                if (exists == false) {
                    friendDTO.setStat(0 + "");
                }
                if (user2 != null && user2.getId() == user1.getId()) {
                    friendDTO.setStat(1 + "");
                }
                list.add(friendDTO);
            }
            peopledto.setFriendDTO(list);
        }
        return peopledto;
    }

    public peopleDTO getUserIntroduce(String account) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        }
        peopleDTO peopleDTO = userutilImp.UserLook(users.get(0), userMapper, questionMapper, commentMapper);
        return peopleDTO;
    }


    public List<UserFriendLISTDTO> froendLook(String account) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        }
       List<UserFriendLISTDTO> list = friendService.friendList(users.get(0));
        return list;
    }

    public List<String> getUserTag(String account, HttpServletRequest request) {
        //首相判断是否已经sessen是否有以前查询过的值
        List<String> userTag1 = (List<String>) request.getSession().getAttribute("userTag"+account);
        if (userTag1!=null){
            return userTag1;
        }else {
            List<String> userTag = userutilImp.getUserTag(account, userMapper, questionMapper, commentMapper);
            request.getSession().setAttribute("userTag"+account,userTag);
            return userTag;
        }
    }
}
