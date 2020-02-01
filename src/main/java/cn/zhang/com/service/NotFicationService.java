package cn.zhang.com.service;

import cn.zhang.com.dto.NoitPaginationDTO;
import cn.zhang.com.dto.NotFicationAndUserDTO;
import cn.zhang.com.enums.NotificationEnum;
import cn.zhang.com.mapper.*;
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
    @Autowired
    private CommentExtMapper commentExtMapper;
    /*根据当前用用户信息拿到所有用户未读信息*/
    public NoitPaginationDTO
    getWeidu(User user, Integer page, Integer size) {
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
            User user1=new User();
            //问题id如果为空或者未-1的或就代表是匿名用户
            if (StringUtils.equals(a.getNotifier(),-1)){
                user1.setName(NotificationEnum.RELY_NIMINGYONGHU.getName());
                user1.setId(NotificationEnum.RELY_NIMINGYONGHU.getType());
            }else {
                 user1 = userMapper.selectByPrimaryKey(Math.toIntExact(a.getNotifier()));
            }
            //数据库如果没有档期那用户的话同样也要是匿名用户
            if (user1 == null) {
                user1.setName(NotificationEnum.RELY_NIMINGYONGHU.getName());
                user1.setId(NotificationEnum.RELY_NIMINGYONGHU.getType());
            }
            notFicationAndUserDTO.setUser(user1);
            //由于点赞记录的是回复评论的id 故需要根据回复评论的id拿到问题id，从而拿到问题内容
            if (StringUtils.equals(a.getType(),NotificationEnum.RELY_DIANZAN.getType())){
                //点赞状态,先根据回复id查到 当前回复的信息
                Comment comment = commentMapper.selectByPrimaryKey(Math.toIntExact(a.getOuterid()));
                //拿到了真正的问题id
                a.setOuterid(comment.getParentId());
            }
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
        List<User> users = userMapper.selectByExample(example1);
        NoitPaginationDTO noitPaginationDTO=new NoitPaginationDTO();
        NotiFicationExample example = new NotiFicationExample();
        if (users.size()!=0){
            example.createCriteria().andReceiverEqualTo(users.get(0).getId().longValue()).andStatusEqualTo(0);//零为未读状态
            return notiFicationMapper.selectByExample(example).size();
        }
        System.out.println("azhang");
        return 0;
    }

    /*根据消息表id，变更信息*/
    @Transactional
    public Integer updateSata(Integer id) {
        NotiFication notiFication = notiFicationMapper.selectByPrimaryKey(id);
        notiFication.setStatus(1);
        notiFicationMapper.updateByPrimaryKey(notiFication);
        /*根据其中的问题id拿到问题信息*/
        //有两种可能 一级回复和二级回复
        if (StringUtils.equals(notiFication.getType(),NotificationEnum.REPLY_QUESTION.getType())){
            /*一级回复--直接拿问题id*/
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(notiFication.getOuterid()));
            return question.getId();
        }
        if (StringUtils.equals(notiFication.getType(),NotificationEnum.REPLY_COMMENT.getType())){
            /*代表是二级回复比较麻烦*/
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria().andParentIdEqualTo(notiFication.getOuterid());
            List<Comment> comment = commentMapper.selectByExample(commentExample);
            Question question = questionMapper.selectByPrimaryKey(Math.toIntExact(comment.get(0).getParentId()));
            return question.getId();
        }
        //点赞，返回问题id
        if (StringUtils.equals(notiFication.getType(),NotificationEnum.RELY_DIANZAN.getType())){
            //先拿到回复评论信息
            Comment comment = commentMapper.selectByPrimaryKey(Math.toIntExact(notiFication.getOuterid()));
            //根据评论信息拿到 问题id 并直接返回
            return Math.toIntExact(comment.getParentId());
        }
        return null;
    }
}
