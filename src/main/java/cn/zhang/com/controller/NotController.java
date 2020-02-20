package cn.zhang.com.controller;

import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.model.User;
import cn.zhang.com.service.DianzanService;
import cn.zhang.com.service.NotFicationService;
import cn.zhang.com.util.Imp.UserutilImp;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotController {
    @Autowired
    private NotFicationService notFicationService;
    @Autowired
    private DianzanService dianzanService;
    @Autowired
    private UserutilImp userutilImp;

    //根据id判断消息是否为已读状态
    @GetMapping("/NotController/{id}")

    private String tongzhi(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        /*更改已读未读状态，返回值为问题的id*/
        Integer panid = notFicationService.updateSata(id);
        /*通知减一*/
        Integer tongzhi = (Integer) request.getSession().getAttribute("tongzhi");
        request.getSession().setAttribute("tongzhi", tongzhi - 1);
        return "redirect:/question/" + panid;
    }

    //根据回复id点赞
    @ResponseBody
    @RequestMapping(value = "/adddianzan", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object dianzanadd(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        System.out.println("fefefefef");
        User user = (User) request.getSession().getAttribute("user");
        //当前用户为空的话提示用户登录
        if (user == null) {
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        //获取到了要点赞的回复id
        String l = jsonObject.get("id").toString();
        System.out.println(l);
        Integer integer = dianzanService.DianzanPanDuan(Integer.valueOf(l), user);
        System.out.println(integer + "{");
        jsonObject.put("id", integer);
        jsonObject.put("code", 200);
        return jsonObject;
    }


    @GetMapping("/NotControllerfriend/{id}")
    //用户点击 好友申请后把消息标为已读
    private String NotControllerfriend(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
        /*更改已读未读状态，返回值为问题的id*/
        User user = (User) request.getSession().getAttribute("user");
        int userid = notFicationService.updatafriendStat(id, request);
        /*通知减一*/
        Integer tongzhi = (Integer) request.getSession().getAttribute("tongzhi");
        request.getSession().setAttribute("tongzhi", tongzhi - 1);
        return "redirect:/people/" + userid;
    }

    //
    @PostMapping("/NoitFriend")
    @ResponseBody
    private String NoitFriend(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        //关注申请用户并将消息表为已读
        String Noitid = jsonObject.get("Noitid").toString();
        notFicationService.NotFriend(Noitid, request);
        return "";
    }

    //请求所给用户id关注
    @PostMapping("/friendNoit")
    @ResponseBody
    private String friendNoit(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String string = jsonObject.get("Noitid").toString();
        notFicationService.friendNoit(string, request);
        return "";
    }
}
