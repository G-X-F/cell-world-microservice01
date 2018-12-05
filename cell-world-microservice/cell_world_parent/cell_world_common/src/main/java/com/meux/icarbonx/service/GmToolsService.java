package com.meux.icarbonx.service;

import com.meux.icarbonx.entities.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "CELL-WORLD-TEST-TOOLS")
public interface GmToolsService {

    @GetMapping("/item/additem")
    public Result issueItems(@RequestParam("rid") long rid, @RequestParam("wid")int wid,@RequestParam("items") String items, @RequestParam("nums")String nums);

//    @PostMapping("/item/setlevel")
//    public Result setRoleLevel(long rid, int wid,int level);
//
//    @PostMapping("/item/sysmail")
//    public Result sendSysMail(int wid,int tempId);
//
//    @PostMapping("/item/patchmail")
//    public Result sendPatchMail(long rid,int wid,int tempId);

    @GetMapping("/test")
    public Result test();

}
