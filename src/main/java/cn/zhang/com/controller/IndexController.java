package cn.zhang.com.controller;

import cn.zhang.com.config.RdisConfig;
import cn.zhang.com.config.WebSocket;
import cn.zhang.com.dto.*;
import cn.zhang.com.model.QuestionExample;
import cn.zhang.com.model.User;
import cn.zhang.com.schedule.HoTaagCache;
import cn.zhang.com.schedule.HotTagTasks;
import cn.zhang.com.service.QuerstionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {


    @Autowired
    private QuerstionService querstionService;
    @Autowired
    private HoTaagCache hoTaagCache;
    /**
     *主页跳转
     * */
    @GetMapping("/")
    public String into(HttpServletRequest request,
                       Model model,
                       @RequestParam(name = "page" ,defaultValue = "1") Integer page,
                       @RequestParam(name = "size",defaultValue = "5") Integer size,
                       @RequestParam(name="search",defaultValue ="false")String search,
                       @RequestParam(name="hot",defaultValue ="false")String hot
                      ) {
        PageQuestionDTO questionDTO=querstionService.friendQuestionAll(page,size,search,hot);
        List<TagsDTO> paixu = hoTaagCache.paixu(hoTaagCache.getTags());
        model.addAttribute("Hotlabel",paixu);
        model.addAttribute("questionDTO",questionDTO);
        if (!StringUtils.equals("false",search)){
            model.addAttribute("search",search);
        }else if(!StringUtils.equals("false",hot)){
            model.addAttribute("hot",hot);
        }
        return "ind";
    }

    //异常测试
    @GetMapping("/error")
    public String error() {
        return "error";
    }

    //
    @GetMapping("index22")
    public String ccc(){

        return "index22";
    }

}
