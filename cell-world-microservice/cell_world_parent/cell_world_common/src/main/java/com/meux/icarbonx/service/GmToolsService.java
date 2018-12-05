package com.meux.icarbonx.service;

import com.meux.icarbonx.entities.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "CELL-WORLD-TEST-TOOLS")
public interface GmToolsService {

    @GetMapping("/item/additem")
    public Result issueItems(long rid, int wid, String items, String nums);

    @GetMapping("/item/setlevel")
    public Result setRoleLevel(long rid, int wid,int level);

    @GetMapping("/item/sysmail")
    public Result sendSysMail(int wid,int tempId);

    @GetMapping("/item/patchmail")
    public Result sendPatchMail(long rid,int wid,int tempId);
}
