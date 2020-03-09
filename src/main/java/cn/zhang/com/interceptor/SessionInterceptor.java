package cn.zhang.com.interceptor;

import cn.zhang.com.dto.RedisD;
import cn.zhang.com.mapper.UserMapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import cn.zhang.com.model.UserExample;
import cn.zhang.com.service.NotFicationService;
import cn.zhang.com.util.Imp.UserutilImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private NotFicationService notFicationService;
    @Autowired
    private UserutilImp userutilImp;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Question question = new Question();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //向数据库查询
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        /*向数据库查询当前用户的未读通知数*/
                        Integer c = notFicationService.getNotFsize(users.get(0).getAccount());
                        request.getSession().setAttribute("tongzhi", c);
                        /*查询最新消息*/
                       Jedis jedis = RedisD.getRedis();
                        String    newestsize = userutilImp.newestsize(jedis, users.get(0).getAccount());
                        request.getSession().setAttribute("newest", newestsize);
                        return true;
                    }
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
