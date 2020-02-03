package cn.zhang.com.service;

import cn.zhang.com.dto.CommentControllerDTO;
import cn.zhang.com.dto.CommentDTO;
import cn.zhang.com.dto.CommenterDTO;
import cn.zhang.com.enums.DianzanEum;
import cn.zhang.com.enums.NotificationEnum;
import cn.zhang.com.mapper.*;
import cn.zhang.com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotiFicationMapper notiFicationMapper;
    @Autowired
    private DianzanMapper dianzanMapper;

    @Transactional//开启事务--当下面任何一条语句执行失败后都将会回滚到最初始状态
    public void insert(User user, CommentDTO commentDTO) {
        //将数据放入容器
        Comment record = new Comment();
        record.setParentId(commentDTO.getParentId());
        //此处有许多要判断的内容，等异常处理学会再统一添加
        record.setCommentator(user.getId());
        record.setContent(commentDTO.getContent());
        record.setType(commentDTO.getType());
        //将回复点赞数初始话
        record.setLikeCount((long) 0);
        record.setCommentCount(0);
        record.setGmtCreate(System.currentTimeMillis());
        record.setGmtModified(record.getGmtCreate());
        //首先判断回复类型：分为 回复，和评论1为回复问题2为回复评论
        if(StringUtils.equals(1,record.getType())){
            //查询回复问题id是否存在
            Integer integer= Math.toIntExact(record.getParentId());
            Question question = questionMapper.selectByPrimaryKey(integer);
            if (question!=null){
                commentMapper.insert(record);
                //增加该问题的回复数
                questionExtMapper.comm(Math.toIntExact(commentDTO.getParentId()));
                //回复问题后，通知被回复的用户
                NotiFication record1 = new NotiFication();
                record1.setGmtCreate(System.currentTimeMillis());
                record1.setNotifier(record.getCommentator().longValue());
                record1.setOuterid(record.getParentId());
                record1.setReceiver(question.getCreator().longValue());
                record1.setType(NotificationEnum.REPLY_QUESTION.getType());
                record1.setStatus(0);
                notiFicationMapper.insert(record1);
            }
            //回复评论
            else {
                Comment comment = commentMapper.selectByPrimaryKey(Math.toIntExact(record.getParentId()));
                if (comment != null) {
                    commentMapper.insert(record);
                }
            }
        }
    }

    public List<CommentControllerDTO> getList(Long parentId, int i, User user1) {
        List<CommentControllerDTO> list=new ArrayList<CommentControllerDTO>();
        CommentExample commentExample=new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(parentId).andTypeEqualTo(i);
        //排序
        commentExample.setOrderByClause("gmt_create desc");
        List<CommentControllerDTO > list1=new ArrayList<>();
        //拿到了所有回复的问题
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size()==0){
            return list1;
        }
        Set<Integer> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        UserExample userExample = new UserExample();
        ArrayList<Integer> values = new ArrayList<>();
        values.addAll(collect);
        userExample.createCriteria().andIdIn(values);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> collect1 = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
         comments.stream().map(comment -> {
            CommentControllerDTO commentControllerDTO=new CommentControllerDTO();
            commentControllerDTO.setComment(comment);
            //判断用户名是否为空，为空则代表时匿名用户
            if (collect1.get(comment.getCommentator()).getName()==null){
                User user=new User();
                user=collect1.get(comment.getCommentator());
                user.setName("匿名用户");
                collect1.put(comment.getCommentator(),user);
            }
            /*根据用户是否登录来判断用户是否点过赞*/
             if (user1!=null) {
                 //想数据库查寻是否有点赞
                 DianzanExample example = new DianzanExample();//要根据当前用户来判断是否点过赞
                 example.createCriteria().andOuteridEqualTo(comment.getId().longValue()).andNotifierEqualTo(user1.getId().longValue());
                 List<Dianzan> dianzans = dianzanMapper.selectByExample(example);
                 if (dianzans.size() != 0) {
                     if (StringUtils.equals(dianzans.get(0).getZhaunttai(), DianzanEum.REPLY_QUESTION.getType()))
                     commentControllerDTO.setDianzan(dianzans.get(0));
                 }
             }
            commentControllerDTO.setUser(collect1.get(comment.getCommentator()));
            list1.add(commentControllerDTO);
            return commentControllerDTO;
        }).collect(Collectors.toList());
        return list1;
    }
    //向数据库添加二级评论信息
    @Transactional
    public Comment addCommenter(CommenterDTO commenterDTO,User user) {
        //使一级评论回复数加一
        commentExtMapper.upda(Math.toIntExact(commenterDTO.getId()));
        //向数据酷添加二级评论内容
        long gmtCreate = System.currentTimeMillis();
        Comment comment = new Comment();
        comment.setParentId(commenterDTO.getId());
        comment.setContent(commenterDTO.getContent());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setGmtCreate(gmtCreate);
        comment.setGmtModified(comment.getGmtCreate());
        comment.setType(2);
        comment.setCommentCount(0);
        commentMapper.insert(comment);
        //回复评论，通知被回复的用户
        NotiFication record1 = new NotiFication();
        record1.setGmtCreate(System.currentTimeMillis());
        record1.setType(NotificationEnum.REPLY_COMMENT.getType());
        record1.setReceiver(comment.getCommentator().longValue());
        //由于时二级评论要先拿到一级评论信息
        Comment comment1 = commentMapper.selectByPrimaryKey(Math.toIntExact(commenterDTO.getId()));
        record1.setOuterid(comment1.getParentId());
        record1.setNotifier(comment.getCommentator().longValue());
        record1.setStatus(0);
        notiFicationMapper.insert(record1);
        //根据时间返回二级评论的所有信息
        CommentExample example = new CommentExample();
        example.createCriteria().andGmtCreateEqualTo(gmtCreate);
        return commentMapper.selectByExample(example).get(0);
    }
}
