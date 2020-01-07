package cn.zhang.com.controller;

import cn.zhang.com.dto.AccessTokenDTO;
import cn.zhang.com.dto.GithubUser;
import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.User;
import cn.zhang.com.provider.GithubProvider;
import cn.zhang.com.service.QuerstionService;
import cn.zhang.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private Usermapper usermapper;
    @Autowired
    private QuerstionService querstionService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_url(Redirect_url);
        //通过已在git注册过的固定信息和地址栏返回信息 再次发送，拿到用户信息
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        //解析返回值
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            //登录成功 写cookie和，session
            //表示用户登录git成功向数据库添加该用户信息
            User user = new User();
            user.setName(githubUser.getName());
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccount(githubUser.getId()+"");
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            user.setAvatar_url(githubUser.getAvatar_url());
            //现向数据库判断是否有该用户
            userService.addUser(user);
            request.getSession().setAttribute("user",user);
            //写入cookie
            Cookie cookie= new Cookie("token",token);
            response.addCookie(cookie);
            //重定向的当前页面--清楚地址栏多余地址以防重复提交
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        HttpServletResponse response){
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        request.getSession().setAttribute("user",null);
        return "redirect:/";
    }
}
