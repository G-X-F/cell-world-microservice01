package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
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
    @GetMapping("/login")
    public User login(String username, String password){
        User user = userFeignService.login(username, password);
        if(null != user){
            request.getSession().setAttribute("account",user);
        }
        return user;
    }


}
