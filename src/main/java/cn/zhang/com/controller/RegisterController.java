package cn.zhang.com.controller;

import cn.zhang.com.dto.ResultDTO;
import cn.zhang.com.exception.CustomizeErrorCode;
import cn.zhang.com.service.RegisterService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhangzheng
 * @date 2020/3/1 13:30
 */
@Controller
public class RegisterController {

    @Autowired(required = false)
    private RegisterService registerService;

    /**
     * 注册页面跳转
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 用户名校验
     */
    @PostMapping("/registerNameVerify")
    @ResponseBody
    public ResultDTO registerNameVerify(@RequestBody JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        if (name.isEmpty()) {
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAME);
        }
        boolean boolName = registerService.NameVerify(name);
        if (boolName) {
            return ResultDTO.okof();
        }
        return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAMECHONGGU);
    }

    /**
     * 密码校验
     */
    @PostMapping("/registerPwdVerify")
    @ResponseBody
    public ResultDTO registerPwdVerify(@RequestBody JSONObject jsonObject) {
        String password = (String) jsonObject.get("password");
        if (password.isEmpty()) {
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAMEPASSOWD);
        }
        Boolean pwdVerify = registerService.PwdVerify(password);
        if (pwdVerify) {
            return ResultDTO.okof();
        } else {
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_NAMEPASSOWDTYPE);
        }
    }
    /**
    *向数据库添加注册用户信息
    * */
    @PostMapping("/registerInsert")
    @ResponseBody
    public ResultDTO registerInsert(@RequestBody JSONObject jsonObject){
        String name = (String) jsonObject.get("name");
        String password = (String) jsonObject.get("password");
        String head = (String) jsonObject.get("head");
        if (name.isEmpty()||password.isEmpty()||head.isEmpty()){
            return ResultDTO.denglu(CustomizeErrorCode.INVALID_CHUCESHIBAI);
        }
        ResultDTO resultDTO=registerService.Insert(name,password,head);
        return resultDTO;
    }
}
