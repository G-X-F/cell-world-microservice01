package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //@Autowired
    //private UserService userService;


    @PostMapping("/user/login")
    public User login(String username, String password) {
        if (username.equals("123") && password.equals("123")) {
            return new User(username, password);
        }
//        User user = new User(username,password);
//        return userService.queryAccount(user);
        return null;
    }

//    @PostMapping("/user/delete")
//    public boolean deleteAccount(Integer uid){
//        return userService.deleteAccount(uid);
//    }


}
