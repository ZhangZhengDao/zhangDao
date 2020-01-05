package cn.zhang.com.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
            HttpServletRequest request,
            Model model
    ){

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //向数据库查询
                    User user = usermapper.findByToken(token);
                    if (user != null) {
                        question.setCreator(user.getId());
                        question.setGmt_create(System.currentTimeMillis());
                        question.setGmt_modified(question.getGmt_create());
                        questionmapper.create(question);
                    }
                    List<QuestionDTO> select = querstionService.select(page, size);
                    model.addAttribute("list",select);
                    return "ind";
                }
            }
        }
        //如果用户未登录将提问内容一并返回
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("error","用户未登录");
        return "publish";
    }
}
