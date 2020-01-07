package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private Usermapper usermapper;

    @Autowired
    private QuerstionService querstionService;

    @GetMapping("/")
    public String into(HttpServletRequest request,
                       Model model,
                       @RequestParam(name = "page",defaultValue = "1") Integer page,
                       @RequestParam(name = "size",defaultValue = "5") Integer size){
        //多表联查查询出所有问题和提出问题的用户
        PaginationDTO select = querstionService.select(null, page,size);
        model.addAttribute("list",select);
        return "ind";
    }



}
