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
        if (username.equals("chunhui") && password.equals("chuihui65586")) {
            return new User(username, password);
        }else if(username.equals("wanghui") && password.equals("wanghui988654")){
            return new User(username, password);
        }else if(username.equals("jianbin") && password.equals("jianbin321546")){
            return new User(username, password);
        }else if(username.equals("guorong") && password.equals("guorong2315647")){
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
