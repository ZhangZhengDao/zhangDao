package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.model.User;
import cn.zhang.com.service.NotFicationService;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfiileContorller {
    @Autowired
    private QuerstionService querstionService;
    @Autowired
    private NotFicationService notFicationService;

    @GetMapping("/profile/{action}")
    public String profile(Model model,
                          @PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "/";
        }
        /*向数据库查询关于当前用户所有提问的信息*/
        if (StringUtils.equals("action",action)) {
            model.addAttribute("model", "action");
            model.addAttribute("zhang1", querstionService.select(user.getAccount(), page, size,  "false", "false","false"));
            model.addAttribute("modelName", "我的提问");
        }
        /*查询当前用户的未读回复*/
        if (StringUtils.equals("zuixinhuifu",action)) {
            /*根据用户ia向数据库查询出所有未读信息*/
            /*分页*/
            model.addAttribute("zhang",notFicationService.getWeidu(user,page,size));
            model.addAttribute("model", "zuixinhuifu");
            model.addAttribute("modelName", "最新回复");
            model.addAttribute("zhang1", new PaginationDTO());
        }
        return "profile";
    }
}
