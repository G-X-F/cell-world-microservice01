package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.service.GmToolsFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stage")
@CrossOrigin(value = "*", allowCredentials = "true")
public class StageController {

    private final GmToolsFeignService gmToolsService;


    @Autowired
    public StageController(GmToolsFeignService gmToolsService) {
        this.gmToolsService = gmToolsService;
    }


    /**
     * 一键解锁关卡
     * @param wid 世界ID
     * @param rid　角色ID
     */
    @PostMapping("/unlockAllStage")
    public Result unlockAllStage(@RequestParam("wid") int wid, @RequestParam("rid") String rid){

        try {
            if(StringUtils.isEmpty(wid)|| StringUtils.isEmpty(rid)) {
                return new Result(Code.ERROR, "参数不能为空");
            }
            long role = Long.parseLong(rid, 16);
            return gmToolsService.unlockAllStage(wid,role);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
