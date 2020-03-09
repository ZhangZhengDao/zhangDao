package cn.zhang.com.controller;

import cn.zhang.com.dto.*;
import cn.zhang.com.schedule.HoTaagCache;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private QuerstionService querstionService;
    @Autowired
    private HoTaagCache hoTaagCache;

    /**
     * 主页跳转
     */
    @GetMapping("/")
    public String into(HttpServletRequest request,
                       Model model,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "size", defaultValue = "5") Integer size,
                       @RequestParam(name = "search", defaultValue = "false") String search,
                       @RequestParam(name = "hot", defaultValue = "false") String hot,
                       @RequestParam(name = "property", defaultValue = "false") String property
    ) {
        PageQuestionDTO questionDTO = querstionService.friendQuestionAll(page, size, search, hot, property);
        List<TagsDTO> paixu = hoTaagCache.paixu(hoTaagCache.getTags());
        model.addAttribute("Hotlabel", paixu);
        model.addAttribute("questionDTO", questionDTO);
        if (!StringUtils.equals(property,"false")){
            model.addAttribute("property",property);
        }else if (!StringUtils.equals("false", search)) {
            model.addAttribute("search", search);
        } else if (!StringUtils.equals("false", hot)) {
            model.addAttribute("hot", hot);
        }
        return "ind";
    }
}
