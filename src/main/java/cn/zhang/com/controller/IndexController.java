package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    @Autowired
    private QuerstionService querstionService;

    @GetMapping("/")
    public String into(HttpServletRequest request,
                       Model model,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                       @RequestParam(name = "sousuo",defaultValue = "false") String sousuo) {
        //多表联查查询出所有问题和提出问题的用户
        PaginationDTO select = querstionService.select(null, page, size,sousuo);
        //将得到的列表排序
        model.addAttribute("list", select);
        /*如何时模糊查询返回值也需要标记*/
        if (!StringUtils.equals(sousuo, "false") && !StringUtils.isEmpty(sousuo)) {
            model.addAttribute("s",sousuo);
        }
        return "ind";
    }

    //异常测试
    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
