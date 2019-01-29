package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.service.GmToolsFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/config")
@CrossOrigin(value = "*",allowCredentials = "true")
public class ConfigController {

    private final GmToolsFeignService gmToolsService;


    @Autowired
    public ConfigController(GmToolsFeignService gmToolsService) {
        this.gmToolsService = gmToolsService;
    }

    @PostMapping("/update")
    public Result updateConfig(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest params =(MultipartHttpServletRequest) request;
        List<MultipartFile> files = params.getFiles("file");
        if(files.size() <= 0)
            return new Result(Code.ERROR,"请选择配置文件");
        MultipartFile[] mfile = new MultipartFile[files.size()];
        int i = 0;
        String savePath = "D:\\hello"+"/";
        File target = new File(savePath);
        if(!target.exists()){
            target.setWritable(true);
            target.mkdirs();
        }
        for(MultipartFile file:files){
            mfile[i] = file;
            i++;
        }
        int wid = Integer.parseInt(params.getParameter("wid"));
        return gmToolsService.updateConfig(wid,mfile);
    }

    @PostMapping("/test")
    public Result testConfig(HttpServletRequest request) {
        MultipartHttpServletRequest params =(MultipartHttpServletRequest) request;
        List<MultipartFile> arr = params.getFiles("file");
        if(arr.size() <= 0)
            return new Result(Code.ERROR,"请选择配置文件");
        MultipartFile mfile = arr.get(0);
        int wid = Integer.parseInt(params.getParameter("wid"));
        return gmToolsService.testConfig(wid,mfile);
    }
}
