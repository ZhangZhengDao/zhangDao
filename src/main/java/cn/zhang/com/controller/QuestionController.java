package cn.zhang.com.controller;

import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.Questionmapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuerstionService querstionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model,
                           HttpServletRequest request){
        //拿到当前问题的id
        QuestionDTO questionDTO = querstionService.getquestionDTO(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }

    //拿到档期那id所有信息然后 去编辑页面
    @GetMapping("/questionBainji/{id}")
    public String questionBainji(@PathVariable(name = "id") Integer id,
                           Model model,
                           HttpServletRequest request){
        //拿到当前问题的id
        QuestionDTO questionDTO = querstionService.getquestionDTO(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        return "publish";
    }
}
