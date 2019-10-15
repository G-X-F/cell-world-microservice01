package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.interceptor.LoginInterceptor;
import com.meux.icarbonx.service.UserFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = "*", allowCredentials = "true")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserFeignService userFeignService;


    @Autowired
    public UserController(UserFeignService userFeignService) {
        this.userFeignService = userFeignService;
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    public User login(HttpServletRequest request,String username, String password) {
        logger.info("请求过来了");
        User user = userFeignService.login(username, password);
        if (null != user) {
            logger.info("查询用户成功");
            request.getSession().setAttribute("account", user);
        }
        return user;
    }

}
