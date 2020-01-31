package cn.zhang.com.controller;

import cn.zhang.com.dto.CommentControllerDTO;
import cn.zhang.com.dto.CommentDTO;
import cn.zhang.com.dto.CommenterDTO;
import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.model.User;
import cn.zhang.com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*评论*/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request, Model model) {//此注解可以将参数通过反射自动封装到对象中
        //判断输入信息是否为空
        if(StringUtils.equals(commentDTO.getContent(),"")){
            return  ResultDTO.denglu(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        //首先需要获取当前用户的id
         User user=(User)request.getSession().getAttribute("user");
         //如果为空就return一个错误码，方便前端识别
        if (user == null) {
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        //向数据库添加
        commentService.insert(user, commentDTO);
         //拿到当前问题的所有回复列表
        return new ResultDTO().okof();
    }

    /*向数据库查找当前id的二级评论*/
    @ResponseBody
    @RequestMapping(value = "/getCommenter/{id}", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public ResultDTO<CommentControllerDTO> getCommenter(@PathVariable Long id,HttpServletRequest request) {//此注解可以将参数通过反射自动封装到对象中
        User user = (User) request.getSession().getAttribute("user");
        List<CommentControllerDTO> comment=commentService.getList(id,2, user);
        return new ResultDTO().okofer(comment);
    }

    /*向数据库添加二级评论内容*/
    @ResponseBody
    @RequestMapping(value = "/Commenter", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Object Commenter(@RequestBody CommenterDTO commenterDTO,HttpServletRequest request) {//此注解可以将参数通过反射自动封装到对象中
        //获取当前评论用户信息
         User user= (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.denglu(CustomizeErrorCode.NO_LOGIN);
        }
        commentService.addCommenter(commenterDTO,user);
        return new ResultDTO().okof();
    }
}
