package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/user/login")
    public User login(String username, String password){
        User user = new User(username,password);
        return userService.queryAccount(user);
    }

    @GetMapping("/user/delete")
    public boolean deleteAccount(Integer uid){
        return userService.deleteAccount(uid);
    }


}
