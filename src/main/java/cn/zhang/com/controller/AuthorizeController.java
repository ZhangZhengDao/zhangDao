package cn.zhang.com.controller;

import cn.zhang.com.dto.AccessTokenDTO;
import cn.zhang.com.dto.GithubUser;
import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.model.User;
import cn.zhang.com.provider.GithubProvider;
import cn.zhang.com.service.NotFicationService;
import cn.zhang.com.service.QuerstionService;
import cn.zhang.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

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

    @Value("${github.Client_id}")
    private String Client_id;
    @Value("${github.Client_secret}")
    private String Client_secret;
    @Value("${github.Redirect_url}")
    private String Redirect_url;

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

    @Autowired
    private NotFicationService notFicationService;

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
}
