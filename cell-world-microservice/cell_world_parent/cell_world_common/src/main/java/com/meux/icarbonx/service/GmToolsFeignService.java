package com.meux.icarbonx.service;

import com.meux.icarbonx.entities.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "CELL-WORLD-TEST-TOOLS")
public interface GmToolsFeignService {

    @RequestMapping(value = "/item/additem",method = {RequestMethod.GET})
    Result issueItems(@RequestParam("rid") long rid, @RequestParam("wid") int wid, @RequestParam("items") String items, @RequestParam("nums") String nums);

    @RequestMapping(value = "/item/setlevel",method = {RequestMethod.POST})
    Result setRoleLevel(@RequestParam("rid") long rid, @RequestParam("wid") int wid, @RequestParam("level") int level);

    @RequestMapping(value = "/item/sysmail",method = {RequestMethod.POST})
    Result sendSysMail(@RequestParam("wid") int wid, @RequestParam("tempId") int tempId);

    @RequestMapping(value = "/item/patchmail",method = {RequestMethod.POST})
    Result sendPatchMail(@RequestParam("rid")long rid,@RequestParam("wid")int wid,@RequestParam("tempId")int tempId);

    @GetMapping("/test")
    Result test();

}
