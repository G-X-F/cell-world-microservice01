package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*",allowCredentials = "true")
public class UserController {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    HttpServletRequest request;

    /**
     * 登陆
     * @param username  用户名
     * @param password  密码
     */
    @PostMapping("/login")
    public User login(String username, String password){
        System.out.println("请求过来了");
        User user = userFeignService.login(username, password);
        System.out.println(user);
        if(null != user){
            request.getSession().setAttribute("account",user);
        }
        return user;
    }


}
