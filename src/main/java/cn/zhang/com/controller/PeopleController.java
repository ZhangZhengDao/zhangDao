package cn.zhang.com.controller;

import cn.zhang.com.dto.UserFriendLISTDTO;
import cn.zhang.com.dto.peopleDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import cn.zhang.com.model.User;
import cn.zhang.com.service.FriendService;
import cn.zhang.com.service.peopleService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class PeopleController {

    @Autowired
    private peopleService peopleService;


    //根据id查询到当前用户的所有信息
    @GetMapping("/people/{id}")
    public String people(@PathVariable(name = "id") String ids,
                         Model model,
                         HttpServletRequest request) {
        Integer id = 0;
        try {
            id = Integer.valueOf(ids);
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        //需要拿到被点赞数，发布问题数，回复数
        User user = (User) request.getSession().getAttribute("user");
        peopleDTO peopledto = peopleService.getPeople(id, user);
        //由于 redis 不熟练 代码有bug 在这里要处理一下--原因：redis后加载，里面没值
        if (user != null) {
            if (peopledto.getStat() == null) {
                if (StringUtils.equals(user.getAccount(), peopledto.getUser().getAccount())) {
                    peopledto.setStat(user.getAccount());
                }
            }
            if (user.getFriend() != null) {
                String[] split = user.getFriend().split(",");
                for (String s : split) {
                    if (StringUtils.equals(id, s)) {
                        peopledto.getUser().setFriend(-1 + "");
                    }
                }
                if (user.getFriend().equals("")) {
                    peopledto.getUser().setFriend(-2 + "");
                }
            }
            //如果是查看自身就不用显示 好友
            if (StringUtils.equals(id, user.getId())) {
                peopledto.getUser().setFriend(0 + "");
            }
        } else {
            peopledto.getUser().setFriend(-3 + "");
        }

        model.addAttribute("people", peopledto);
        return "people";
    }

    /*拿到所给account的所有信息*/
    @RequestMapping("/getUserIntroduce")
    @ResponseBody
    public String getUserIntroduce(@RequestBody JSONObject jsonObject,HttpServletRequest request) {
        //查询当前用户的所有信息
        String account = jsonObject.get("account").toString();
        peopleDTO peopleDTO = peopleService.getUserIntroduce(account);
        //查询当前用户好友的所有信息
        List<UserFriendLISTDTO> userFriendLISTDTO = peopleService.froendLook(account);
        //计算用户经常回答的问题标签
        List<String> UserTag=peopleService.getUserTag(account,request);
        jsonObject.put("peopleDTO",peopleDTO);
        jsonObject.put("userfriendList",userFriendLISTDTO);
        jsonObject.put("UserTag",UserTag);
        return jsonObject.toJSONString();
    }
}
