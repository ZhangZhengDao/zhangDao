package cn.zhang.com.controller;

import cn.zhang.com.advice.TagCache;
import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.TagDTO;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuerstionService querstionService;

    @GetMapping("/publish")
    public String gitpublish(Model model) {
        /*将所有标签库发送到页面*/
        model.addAttribute("ccc", TagCache.getTagCache());
        return "publish";
    }

    @PostMapping("/publish")
    public String postpublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            Integer id,
            HttpServletRequest request,
            Model model,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {

        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);
            model.addAttribute("error", "用户未登录");
            return "publish";
        } else {
            /*判断用户选择标签是是否符合规格*/
            String[] split = tag.split(",");
            int c=split.length;
            int a=0;
            for (TagDTO tagDTO : TagCache.getTagCache()) {
                    for (String s1 : split) {
                        for (String s : tagDTO.getName()) {
                            if (StringUtils.equals(s,s1)){
                            a++;
                        }
                    }
                }
            }
            if (a!=c){
                model.addAttribute("title", title);
                model.addAttribute("description", description);
                model.addAttribute("tag", tag);
                model.addAttribute("error", "标签不符合规范");
                model.addAttribute("ccc", TagCache.getTagCache());
                return "publish";
            }
            //在此处选择添加还是修改
            User user1 = (User) user;
            Question question = new Question();
            question.setId(id);
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            //将评论数点赞数阅读数初始化
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCreator(user1.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            //给发起提问添加状态 如果有当前提问就只用修改，没有就去数据酷添加
            querstionService.AddQuestionORUpdate(question, user1);
            PaginationDTO select = querstionService.select(null, page, size, null);
            model.addAttribute("list", select);
            return "ind";
        }

    }

}
