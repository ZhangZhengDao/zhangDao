package cn.zhang.com.controller;

import cn.zhang.com.dto.*;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.model.User;
import cn.zhang.com.provider.GithubProvider;
import cn.zhang.com.service.NotFicationService;
import cn.zhang.com.service.QuerstionService;
import cn.zhang.com.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    //github
    @Value("${github.Client_id}")
    private String Client_id;
    @Value("${github.Client_secret}")
    private String Client_secret;
    @Value("${github.Redirect_url}")
    private String Redirect_url;
    //码云
    @Value("${mayun.Client_id}")
    private String mayun_Client_id;
    @Value("${mayun.Client_secret}")
    private String mayun_Client_secret;
    @Value("${mayun.Redirect_url}")
    private String mayun_Redirect_url;

    @Autowired
    private NotFicationService notFicationService;

    @Autowired
    private QuerstionService querstionService;
    /*@GetMapping("/")*/
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        /*判断是否已经有了cookie值*/
        User user2=(User) request.getSession().getAttribute("user");
        if (user2!=null){
            return "redirect:/";
        }
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_url(Redirect_url);
        //通过已在git注册过的固定信息和地址栏返回信息 再次发送，拿到用户信息
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //解析返回值 System.out.println(githubUser.toString());
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser.getName()==null){
            return "redirect:https://github.com/login/oauth/authorize?client_id="+Client_id+"&redirect_url=http://localhost:8887//callback&scope=user&state=1";
        }
        if (!githubUser.getName().isEmpty()) {
            //登录成功 写cookie和，session
            //表示用户登录git成功向数据库添加该用户信息
            User user = new User();
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount(githubUser.getId() + "");
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            //现向数据库判断是否有该用户
            user = userService.addUser(user);
            if (user==null){
                return "redirect:/";
            }
            /*向数据库查询当前用户的未读通知数*/
            Integer c=notFicationService.getNotFsize(user.getAccount());
            request.getSession().setAttribute("tongzhi",c);
            request.getSession().setAttribute("user", user);
            //写入cookie
            Cookie cookie = new Cookie("token", user.getToken());
            response.addCookie(cookie);
            //重定向的当前页面--清楚地址栏多余地址以防重复提交
            return "redirect:/";
        }
        return "redirect:/";

    }

   /*码云第三方登录*/
    @RequestMapping(value = "/mayun",method = RequestMethod.GET)
    public String mayun(@RequestParam(name = "code",defaultValue = "false") String code
                   ){
        System.out.println(code);
        return "redirect:/";
    }

    /*退出登录*/
    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        request.getSession().setAttribute("user", null);
        return "redirect:/";
    }
    /*github登录地址*/
    @RequestMapping("/github")
    public String github(HttpServletRequest request,
                         HttpServletResponse response) {
        return "redirect:https://github.com/login/oauth/authorize?client_id="+Client_id+"&redirect_url=http://localhost:8887//callback&scope=user&state=1";
    }
    /*码云登录地址*/
    @RequestMapping("/mayunDenglu")
    public String mayun(HttpServletRequest request,
                         HttpServletResponse response) {
        return "redirect:https://gitee.com/oauth/authorize?client_id="+mayun_Client_id+"&redirect_url=http://127.0.0.1:8887/mayun&response_type=code";
    }

    /*普通用户登录判断*/
    @ResponseBody
    @RequestMapping(value = "/Denglu" ,method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResultDTO Denglu(@RequestBody JSONObject jsonpObject,HttpServletRequest request,HttpServletResponse response){
        //根据内容判断用户是否登录成功
        User user=new User();
        user.setName(jsonpObject.get("name").toString());
        user.setPasswd(jsonpObject.get("password").toString());
        user=userService.Denglu(user);
        if (user==null){
        return   new ResultDTO<>().denglu(CustomizeErrorCode.INVALID_DANGLE);
        }
        //写入cookie和session
        /*向数据库查询当前用户的未读通知数*/
        Integer c=notFicationService.getNotFsize(user.getAccount());
        request.getSession().setAttribute("tongzhi",c);
        request.getSession().setAttribute("user", user);
        //写入cookie
        Cookie cookie = new Cookie("token", user.getToken());
        response.addCookie(cookie);
        return   new ResultDTO<>().denglu(CustomizeErrorCode.INVALID_DECLENSION);
    }
}
