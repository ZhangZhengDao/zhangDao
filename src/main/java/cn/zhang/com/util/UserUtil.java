package cn.zhang.com.util;

import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.dto.RedisD;
import cn.zhang.com.dto.peopleDTO;
import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.CommentExample;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface UserUtil {
    //增加用户未读消息
    public void Userunread(Jedis jedis,String daccount,String text,User user);
    /*得到未读消息的数量*/
    public String newestsize(Jedis jedis,String account);
    /*拿到用户所有信息*/
    public peopleDTO UserLook(User user,  UserMapper userMapper, QuestionMapper questionMapper, CommentMapper commentMapper);
    /*计算出用户经常提问 和 回复的问题标签*/
    public List<String> getUserTag(String account, UserMapper userMapper, QuestionMapper questionMapper, CommentMapper commentMapper);
    //根据account查询出用户的信息
    public User AccountfidUser(String account);
    //根据id查询出用户的信息
    public User IDfidUser(String id);
    //根据集合查询出用户信息
    public List<QuestionDTO> findUserAndQuestionAll(List<Question> list);

}
