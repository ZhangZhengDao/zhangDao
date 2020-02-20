package cn.zhang.com.dto;

import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.QuestionMapper;
import cn.zhang.com.model.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class peopleDTO {

    //需要拿到被点赞数，发布问题数，回复数
    private Integer dinazan;
    private Integer wentishu;
    private Integer Qhuifu;
    private Integer yuedu;
    //评论回复数
    private Integer pinglunhuifu;
    //问题回复数
    private Integer Whuifu;
    //用户
    private User user;
    //问题
    private List<Question> questionList;
    //回复内容
    private List<Comment> comments;
    //用户年龄
    private String Data;
    //用户是否在线
    private String stat;
    //好友的信息
    private List<friendDTO> friendDTO;

    //根据用户信息查到当前用户所有信息
/*    public void geUser(User user){
        //拿到当前用户发布的问题
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(user.getId());
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(example);
        //拿到发布问题数
        this.wentishu =questions.size();
        //拿到所有问题
        this.questionList=questions;
        for (Question question1 : questions) {
            this.Qhuifu=this.Qhuifu+question1.getCommentCount();
            this.yuedu=this.yuedu+question1.getViewCount();
        }
        //拿出当前用户回复问题的数量
        CommentExample example1 = new CommentExample();
        example1.createCriteria().andCommentatorEqualTo(user.getId());
        List<Comment> comments = commentMapper.selectByExample(example1);
        this.Whuifu=comments.size();
        //计算所有问答的点赞数
        for (Comment comment : comments) {
            this.dinazan=Math.toIntExact(comment.getLikeCount());
            this.pinglunhuifu=this.pinglunhuifu+comment.getCommentCount();
        }
    }*/
}
