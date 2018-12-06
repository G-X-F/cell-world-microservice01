package com.meux.icarbonx.service;

import com.meux.icarbonx.entities.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CELL-WORLD-USER")
public interface UserFeignService {


    @GetMapping("/user/login")
    User login(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/user/delete")
    boolean deleteAccount(@RequestParam("uid") Integer uid);

}
