package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.service.GmToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CellWordController {

    @Autowired
    private GmToolsService gmToolsService;

    @GetMapping("/test")
    public Result issueItems(){
        System.out.print("hello");
        return gmToolsService.issueItems(1002,2,"","");
    }
}
