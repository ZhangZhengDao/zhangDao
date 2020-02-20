package cn.zhang.com.controller;

import cn.zhang.com.advice.TagCache;
import cn.zhang.com.dto.CommentControllerDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.dto.TagDTO;
import cn.zhang.com.enums.CommentTypeEnum;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import cn.zhang.com.exception.ICustomizeErrorCode;
import cn.zhang.com.model.Comment;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import cn.zhang.com.service.CommentService;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuerstionService querstionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String Sid,
                           Model model,
                           HttpServletRequest request) {
        //先判断地址栏内容是否符合要求
        Integer id=0;
        try {
            id=Integer.valueOf(Sid);
        }catch (Exception e){
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        //拿到当前问题的id的内容
        QuestionDTO questionDTO = querstionService.getquestionDTO(id);
        //如果为空的就表示没有当前问题抛出异常
        if (questionDTO==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        long L=id;
        /*当前用户是否点赞需要用户信息，判断用户是否登录*/
        User user= (User) request.getSession().getAttribute("user");
        //拿到所有回复的问题
        List<CommentControllerDTO> comment=commentService.getList(L,1,user);
        //mysql正则表达式模糊匹配
        List<Question> questions=querstionService.likegetListCommentType2(questionDTO);
        model.addAttribute("likeType2",questions);
        model.addAttribute("question", querstionService.getquestionDTO(id));
        model.addAttribute("type1",comment);
        return "question";
    }

    //拿到档期那id所有信息然后 去编辑页面
    @GetMapping("/questionBainji/{id}")
    public String questionBainji(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 HttpServletRequest request) {
        //拿到当前问题的id
        QuestionDTO questionDTO = querstionService.getquestionDTO(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("title", questionDTO.getQuestion().getTitle());
        model.addAttribute("description", questionDTO.getQuestion().getDescription());
        model.addAttribute("tag", questionDTO.getQuestion().getTag());
        model.addAttribute("ccc", TagCache.getTagCache());
        return "publish";
    }



}
