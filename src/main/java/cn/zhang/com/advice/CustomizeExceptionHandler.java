package cn.zhang.com.advice;

import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.spi.CurrencyNameProvider;

/*异常处理*/
@ControllerAdvice
public class CustomizeExceptionHandler {


    @ExceptionHandler(Exception.class)
        // 在此设置全局异常处理，可以通过value过滤请拦截的条件
        // @ExceptionHandler  // 在此设置全局异常处理，可以通过value过滤请求，默认是全部
    ModelAndView excepetion(Throwable e, HttpServletRequest request, Model model, HttpServletResponse response) {
        //拿到请求方式去判断
        String contentType = request.getContentType();
        //如果是json就返回json数据，可以实现实时交互
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO=  ResultDTO.errof((CustomizeException) e);
            } else {
                resultDTO= ResultDTO.denglu(CustomizeErrorCode.SYS_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;

        } else {
            //如果是其他错误就返回错误页面
            //判断是否是识别的异常类型
            if (e instanceof CustomizeException) {
                model.addAttribute("massage", e.getMessage());
            } else {
                model.addAttribute("massage", CustomizeErrorCode.SYS_ERROR);
            }
            return new ModelAndView("error");

        }

    }

}
