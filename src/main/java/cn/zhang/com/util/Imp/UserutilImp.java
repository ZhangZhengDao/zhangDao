package cn.zhang.com.util.Imp;

import cn.zhang.com.dto.*;
import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.*;
import cn.zhang.com.util.UserUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

@Component
public class UserutilImp implements UserUtil {
    @Autowired
    public UserMapper userMapper;

    @Override
    public void Userunread(Jedis jedis, String daccount, String text, User user) {
        Boolean aBoolean = jedis.exists("newestMap" + daccount);
        if (aBoolean) {
            List<String> hmget = jedis.hmget("newestMap" + daccount, user.getAccount());
            if (hmget.size() != 0) {
                jedis.hincrBy("newestMap" + daccount, user.getAccount(), 1);
                jedis.hset("newestMap" + daccount, "text" + user.getAccount(), text);
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("text" + user.getAccount(), text);
                map.put(user.getAccount(), 1 + "");
                jedis.hmset("newestMap" + daccount, map);
            }
        } else {
            Map<String, String> map = new HashMap<>();
            map.put(user.getAccount(), 1 + "");
            map.put("text" + user.getAccount(), text);
            jedis.hmset("newestMap" + daccount, map);
        }
    }

/*
*
* 获取用户的最新未读消息*/
    @Override
    public String  newestsize(Jedis jedis, String account) {
        Boolean exists = jedis.exists("newestMap" + account);
        if (exists==false){
            return "";
        }
         List<String> c=new ArrayList<>();
        if (exists==true){
            Map<String, String> map = jedis.hgetAll("newestMap" + account);
            map.forEach((k,v)->{
                if (k.contains("text")==false&&k.contains("data")==false){
                    c.add(v);
                }
            });
        }
        return c.get(0);
    }

    @Override
    public peopleDTO UserLook(User user, UserMapper userMapper, QuestionMapper questionMapper, CommentMapper commentMapper) {
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
//        peopledto.setQuestionList(questions);
        //拿到所有回复内容
//        peopledto.setComments(comments);
        //用户年龄
        Long dat = System.currentTimeMillis() - user.getGmtModified();
        peopledto.setData(dat / (1000 * 3600 * 24) + "");
        //获取用户在线状态
        Jedis jedis = RedisD.getRedis();
        Boolean mykey = jedis.exists(user.getAccount());
        if (mykey) {
            peopledto.setStat(1 + "");
        }
        return peopledto;
    }

    @Override
    public List<String> getUserTag(String account, UserMapper userMapper, QuestionMapper questionMapper, CommentMapper commentMapper) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        }
        //提取出5个用户常用的标签
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(users.get(0).getId());
        List<Question> questions = questionMapper.selectByExample(example1);
        Map<String, Integer> Maptag = new HashMap<>();
        for (Question question : questions) {
            String[] split = question.getTag().split(",");
            for (String s : split) {
                if (Maptag.containsKey(s)) {
                    Maptag.put(s, Maptag.get(s) + 10);
                    continue;
                }
                Maptag.put(s, 10);
            }
        }
        //提取到了所有标签 提取所有问题的回复标签
        CommentExample example2 = new CommentExample();
        example2.createCriteria().andCommentatorEqualTo(users.get(0).getId());
        List<Comment> comments = commentMapper.selectByExample(example2);
        //查看用户评论问题的标签
        for (Comment comment : comments) {
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(comment.getParentId()));
            if (question == null) {
                continue;
            }
            String[] split = question.getTag().split(",");
            for (String s : split) {
                if (Maptag.containsKey(s)) {
                    Maptag.put(s, Maptag.get(s) + 1);
                    continue;
                }
                Maptag.put(s, 2);
            }
        }
        //排序
        List<Map.Entry<String, Integer>> lstEntry = new ArrayList<>(Maptag.entrySet());
        Collections.sort(lstEntry, ((o1, o2) -> {
            return o1.getValue().compareTo(o2.getValue());
        }));
        List<String> afd = new ArrayList<>();
        for (int i = lstEntry.size() - 1; i >= 0; i--) {
            if (lstEntry.get(i).getValue().equals("2")) {
                continue;
            }
            if (afd.size() == 4) {
                break;
            }
            afd.add(lstEntry.get(i).getKey());
        }
        return afd;
    }

    @Override
    public User AccountfidUser(String account) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountEqualTo(account);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() != 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User IDfidUser(String id) {
        User users = userMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (users != null) {
            return users;
        }
        return null;
    }

    @Override
    public List<QuestionDTO> findUserAndQuestionAll(List<Question> list) {
        List<QuestionDTO> QuestionList = new ArrayList<>();
        Map<Integer, User> UserIdMap = new HashMap<>();
        for (Question question : list) {
            QuestionDTO questionDTO = new QuestionDTO();
            Integer creator = question.getCreator();
            if (UserIdMap.containsKey(creator)) {
                questionDTO.setUser(UserIdMap.get(creator));
            } else {
                User user = userMapper.selectByPrimaryKey(creator);
                UserIdMap.put(creator, user);
                questionDTO.setUser(user);
            }
            questionDTO.setQuestion(question);
            QuestionList.add(questionDTO);
        }
        return QuestionList;
    }


}
