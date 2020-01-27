package cn.zhang.com.controller;

import cn.zhang.com.service.NotFicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotController {
    @Autowired
    private NotFicationService notFicationService;

    @GetMapping("/NotController/{id}")
    private String tongzhi(@PathVariable(name = "id") Integer id, HttpServletRequest request){
        /*更改已读未读状态，返回值为问题的id*/
       Integer panid= notFicationService.updateSata(id);
       /*通知减一*/
        Integer tongzhi= (Integer) request.getSession().getAttribute("tongzhi");
        request.getSession().setAttribute("tongzhi",tongzhi-1);
        return "redirect:/question/"+panid;
    }
}
