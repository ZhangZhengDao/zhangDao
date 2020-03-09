package cn.zhang.com.controller;

import cn.zhang.com.mapper.CommentMapper;
import cn.zhang.com.mapper.ccMapper;
import cn.zhang.com.model.Comment;
import cn.zhang.com.model.CommentExample;
import cn.zhang.com.model.cc;
import cn.zhang.com.model.ccExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Zhangzheng
 * @date 2020/3/3 15:20
 */
@Controller
public class TextController {

    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/comm")
    public String Comment(){
        CommentExample example = new CommentExample();
        List<Comment> ccs = commentMapper.selectByExampleWithRowbounds(example, new RowBounds(0, 5));
        System.out.println(ccs.toString());
        return "redirect:/";
    }
}
