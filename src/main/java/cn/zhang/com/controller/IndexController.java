package cn.zhang.com.controller;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.dto.QuestionDTO;
import cn.zhang.com.dto.TagsDTO;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.schedule.HoTaagCache;
import cn.zhang.com.schedule.HotTagTasks;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private QuerstionService querstionService;
    @Autowired
    private HoTaagCache hoTaagCache;

    @GetMapping("/")
    public String into(HttpServletRequest request,
                       Model model,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                       @RequestParam(name = "sousuo",defaultValue = "false") String sousuo,
                       @RequestParam(name = "redu",defaultValue = "false") String redu
                      ) {
        String remen=(String)request.getSession().getAttribute("remen");
        //拿到相关的热门问题
        List<TagsDTO> paixu = hoTaagCache.paixu(hoTaagCache.getTags());
        model.addAttribute("renmen",paixu);
        //用户点击热度标签后需要返回带有标签的相关问题
        //多表联查查询出所有问题和提出问题的用户
        PaginationDTO select = querstionService.select(null, page, size,sousuo,redu,remen);
        //将得到的列表排序
        model.addAttribute("list", select);
        /*如何时模糊查询返回值也需要标记*/
        if (!StringUtils.equals(sousuo, "false") && !StringUtils.isEmpty(sousuo)) {
            model.addAttribute("s",sousuo);
        }
        request.getSession().setAttribute("remen","false");
        /*点击热度后需要给返回值*/
        if (!StringUtils.equals(redu,"false")){
            request.getSession().setAttribute("remen",redu);
        }
        return "ind";
    }

    //异常测试
    @GetMapping("/error")
    public String error() {
        return "error";
    }


}
