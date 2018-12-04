package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public User login(String username, String password){
        User user = new User(username,password);
        return userService.queryAccount(user);
    }

    @GetMapping("/delete/{uid}")
    public boolean deleteAccount(@PathVariable(value = "uid") Integer uid){
        return userService.deleteAccount(uid);
    }

}
