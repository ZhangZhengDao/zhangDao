package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.mapper.Questionmapper;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private Questionmapper questionmapper;
    @Autowired
    private Usermapper usermapper;
    @Autowired
    private QuerstionService querstionService;
    @GetMapping("/publish")
    public String gitpublish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String postpublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            Long id,
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "5") Integer size
    ){

        Object user = request.getSession().getAttribute("user");
        if (user==null) {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            model.addAttribute("error", "用户未登录");
            return "publish";
        }else {
            //在此处选择添加还是修改
            User user1= (User) user;
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreator(user1.getId());
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            //给发起提问添加状态 如果有当前提问就只用修改，没有就去数据酷添加
            querstionService.AddQuestionORUpdate(question,user1);
            PaginationDTO select = querstionService.select(null, page, size);
            System.out.println(select.getQuestionDTOS().size());
            model.addAttribute("list", select);
            return "ind";
        }

    }

}
