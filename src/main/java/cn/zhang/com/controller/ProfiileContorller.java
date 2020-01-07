package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.model.User;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ProfiileContorller {
    @Autowired
    private QuerstionService querstionService;
    @GetMapping("/profile/{action}")
    public String profile(Model model,
                          @PathVariable(name = "action")String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        User user= (User) request.getSession().getAttribute("user");
        if("action".equals("action")){
            model.addAttribute("model","action");
            model.addAttribute("modelName","我的提问");
        }
        if("zuixinhuifu".equals(action)){
            model.addAttribute("model","zuixinhuifu");
            model.addAttribute("modelName","最新回复");
        }
        PaginationDTO select = querstionService.select(user.getAccount(),page,size);
        model.addAttribute("list",select);
        return "profile";
    }
}
