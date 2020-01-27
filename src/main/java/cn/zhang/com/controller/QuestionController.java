package cn.zhang.com.controller;

import cn.zhang.com.advice.TagCache;
import cn.zhang.com.dto.CommentControllerDTO;
import cn.zhang.com.dto.QuestionDTO;
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
    public String question(@PathVariable(name = "id") Integer id,
                           Model model,
                           HttpServletRequest request) {
        //拿到当前问题的id的内容
        QuestionDTO questionDTO = querstionService.getquestionDTO(id);
        long L=id;
        //拿到所有回复的问题
        List<CommentControllerDTO> comment=commentService.getList(L,1);
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
