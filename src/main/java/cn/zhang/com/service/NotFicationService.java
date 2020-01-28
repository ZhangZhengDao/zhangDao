package cn.zhang.com.service;

import cn.zhang.com.dto.NoitPaginationDTO;
import cn.zhang.com.dto.NotFicationAndUserDTO;
import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.NotiFicationMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotFicationService {

    @Autowired
    private NotiFicationMapper notiFicationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    /*根据当前用用户信息拿到所有用户未读信息*/
    public NoitPaginationDTO getWeidu(User user, Integer page, Integer size) {
        NoitPaginationDTO noitPaginationDTO=new NoitPaginationDTO();
        NotiFicationExample example = new NotiFicationExample();
        example.createCriteria().andReceiverEqualTo(user.getId().longValue()).andStatusEqualTo(0);//零为未读状态
        Integer c = size * (page - 1);
        RowBounds rowBounds = new RowBounds(c,size);
        List<NotiFication> list = notiFicationMapper.selectByExampleWithRowbounds(example, rowBounds);
        List<NotFicationAndUserDTO> list1=new ArrayList<>();
        list.stream().forEach(a->{
            NotFicationAndUserDTO notFicationAndUserDTO=new NotFicationAndUserDTO();
//            if (!StringUtils.equals(a.getNotifier(),user.getId())) {
                notFicationAndUserDTO.setUser(userMapper.selectByPrimaryKey(Math.toIntExact(a.getNotifier())));
                notFicationAndUserDTO.setQuestion(questionMapper.selectByPrimaryKey(Math.toIntExact(a.getOuterid())));
                notFicationAndUserDTO.setNotiFication(a);
                list1.add(notFicationAndUserDTO);
//            }
        });
        //查询出内容
        noitPaginationDTO.setNotFicationAndUserDTOlist(list1);
        //给到数据库数据总数算出页数
        int size1 = notiFicationMapper.selectByExample(example).size();
        noitPaginationDTO.getfenyeshu(size1);
        return noitPaginationDTO;
    }

    /*根据用户获取通知数*/
    public Integer getNotFsize(String account) {
        UserExample example1 = new UserExample();
        example1.createCriteria().andAccountEqualTo(account);
        System.out.println("我要向数据库查询了");
        List<User> users = userMapper.selectByExample(example1);
        System.out.println("查询成功");
        System.out.println(users.toString());
        NoitPaginationDTO noitPaginationDTO=new NoitPaginationDTO();
        NotiFicationExample example = new NotiFicationExample();
        example.createCriteria().andReceiverEqualTo(users.get(0).getId().longValue()).andStatusEqualTo(0);//零为未读状态
        return notiFicationMapper.selectByExample(example).size();
    }

    /*根据消息表id，变更信息*/
    @Transactional
    public Integer updateSata(Integer id) {
        NotiFication notiFication = notiFicationMapper.selectByPrimaryKey(id);
        notiFication.setStatus(1);
        notiFicationMapper.updateByPrimaryKey(notiFication);
        /*根据其中的问题id拿到问题信息*/
        //有两种可能 一级回复和二级回复
        if (StringUtils.equals(notiFication.getType(),"1")){
            /*一级回复--直接拿问题id*/
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(notiFication.getOuterid()));
            return question.getId();
        }
        if (StringUtils.equals(notiFication.getType(),"2")){
            /*代表时二级回复比较麻烦*/
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andParentIdEqualTo(notiFication.getOuterid());
            List<Comment> comment = commentMapper.selectByExample(commentExample);
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(comment.get(0).getParentId()));
            return question.getId();
        }
        return null;
    }
}
