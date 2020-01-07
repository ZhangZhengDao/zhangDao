package cn.zhang.com.interceptor;

import cn.zhang.com.dto.PaginationDTO;
import cn.zhang.com.mapper.Questionmapper;
import cn.zhang.com.mapper.Usermapper;
import cn.zhang.com.model.Question;
import cn.zhang.com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private Usermapper usermapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        Question question = new Question();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //向数据库查询
                    User user = usermapper.findByToken(token);
                    if (user != null) {
                     request.getSession().setAttribute("user",user);
                        return true;
                    }
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
