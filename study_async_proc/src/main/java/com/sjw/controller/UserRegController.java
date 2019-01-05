package com.sjw.controller;

import com.sjw.service.IUserReg;
import com.sjw.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试 用户注册 的控制器
 */
@Controller
public class UserRegController {

    private static final String SUCCESS = "suc";
    private static final String FAILURE = "failure";

    //更改@Qualifier的名字分别试验3种情况 para、serial、async
    @Autowired
    @Qualifier("async")
    private  IUserReg userReg;

    @RequestMapping("/userReg")
    public String userReg(){
        return "index";
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public String saveUser(@RequestParam("userName")String userName,
                           @RequestParam("email")String email,
                           @RequestParam("phoneNumber")String phoneNumber){
        try {
            if (userReg.userRegister(User.makeUser(userName,email,phoneNumber)))
                return SUCCESS;
            else
                return FAILURE;
        } catch (Exception e) {
            return FAILURE;
        }
    }


}
