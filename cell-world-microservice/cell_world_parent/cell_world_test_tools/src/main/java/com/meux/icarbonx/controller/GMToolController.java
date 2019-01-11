package com.meux.icarbonx.controller;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.configuration.CommandConfig;
import com.meux.icarbonx.configuration.FileConfig;
import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.proto.*;
import com.meux.icarbonx.service.TestToolService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.meux.icarbonx.utis.Avatas.Array2List;

@RestController
public class GMToolController {

    private final TestToolService toolService;

    private final CommandConfig config;

    private final FileConfig fileConfig;

    @Autowired
    public GMToolController(TestToolService toolService, CommandConfig config, FileConfig fileConfig) {
        this.toolService = toolService;
        this.config = config;
        this.fileConfig = fileConfig;
    }

    /**
     * 发放道具
     */
    @GetMapping("/item/additem")
    public Result issueItems(long rid, int wid, String items, String nums){
        try {
            if(StringUtils.isEmpty(items)|| StringUtils.isEmpty(nums)){
                return new Result(Code.ERROR,"参数不能为空");
            }
            String[] itemstr = items.split(",");
            String[] numstr = nums.split(",");
            List<Integer> item = Array2List(itemstr);
            List<Integer> num = Array2List(numstr);

            PbItem.AddItem.Builder body = PbItem.AddItem.newBuilder();
            body.setRid(rid);
            body.setWid(wid);
            body.addAllItemId(item);
            body.addAllNums(num);

            ProtobuffFrame.Request.Builder request = ProtobuffFrame.Request.newBuilder();
            request.setCmd(config.getCmd());
            request.setSub(config.getSub_03());
            request.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(request.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"发放成功");
            }
            return new Result(Code.ERROR,"发放失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置角色等级
     */
    @PostMapping("/item/setlevel")
    public Result setRoleLevel(long rid, int wid,int level){
        try {
            if(StringUtils.isEmpty(rid) ||StringUtils.isEmpty(wid)||StringUtils.isEmpty(level) ){
                return new Result(Code.ERROR,"参数不能为空");
            }
            PbItem.SetRoleLevel.Builder body = PbItem.SetRoleLevel.newBuilder();
            body.setRid(rid);
            body.setWid(wid);
            body.setLevel(level);

            ProtobuffFrame.Request.Builder request = ProtobuffFrame.Request.newBuilder();
            request.setCmd(config.getCmd());
            request.setSub(config.getSub_04());
            request.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(request.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"等级设置成功");
            }
            return new Result(Code.ERROR,"等级设置失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送全服邮件
     */
    @PostMapping("/item/sysmail")
    public Result sendSysMail(int wid,int tempId){
        try {
            if(StringUtils.isEmpty(wid)|| StringUtils.isEmpty(tempId)) {
                return new Result(Code.ERROR, "参数不能为空");
            }
            PbItem.SysMail.Builder body = PbItem.SysMail.newBuilder();
            body.setWid(wid);
            body.setTempId(tempId);

            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(config.getCmd());
            msg.setSub(config.getSub_01());
            msg.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(msg.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"邮件发送成功");
            }
            return new Result(Code.ERROR,"邮件发送失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送定向邮件
     */
    @PostMapping("/item/patchmail")
    public Result sendPatchMail(long rid,int wid,int tempId){
        try {
            if(StringUtils.isEmpty(wid)|| StringUtils.isEmpty(tempId)||StringUtils.isEmpty(rid)) {
                return new Result(Code.ERROR, "参数不能为空");
            }
            PbItem.PatchMail.Builder body = PbItem.PatchMail.newBuilder();
            body.setWid(wid);
            body.setTempId(tempId);
            body.setRid(rid);

            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(config.getCmd());
            msg.setSub(config.getSub_02());
            msg.setBody(body.build().toByteString());
            boolean b = toolService.sendTo(msg.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"邮件发送成功");
            }
            return new Result(Code.ERROR,"邮件发送失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/test")
    public Result test(){
        return new Result(1,"hello");
    }

//    @PostMapping("/config/update")
//    public Result updateConfig(@RequestParam("files") MultipartFile[] files ) throws IOException {
//        if(files.length == 0){
//            return new Result(Code.ERROR, "文件列表为空，上传失败");
//        }
//
//        for(MultipartFile file : files){
//            if(!file.isEmpty()){
//                //获取文件名
//                String oldFileName=file.getOriginalFilename();
//                //获取文件名后缀
//                String type = oldFileName.contains(".")?oldFileName.substring(oldFileName.lastIndexOf(".")+1):null;
//                String fileType = fileConfig.getFileType();
//                //校验文件类型
//                if(type != null && fileType.contains(type)){
//                    //文件存储地址
////                    String targetPath = fileConfig.getTargetPath();
////                    File f = new File(targetPath + oldFileName);
////                    file.transferTo(f);
//
//                    //向配置服推送数据
//                    ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
//                    msg.setCmd(1110);
//                    msg.setSub(2);
//                    msg.setBody(ByteString.copyFrom(file.getBytes()));
//                    boolean b = toolService.sendTo(msg.build(), "http://127.0.0.1:9010");
//                    if(b){
//                        return new Result(Code.SUCCESS,"配置更新成功");
//                    }
//                    return new Result(Code.ERROR,"配置更新失败");
//                }
//            }
//        }
//
//        return null;
//    }


    @PostMapping("/config/update")
    public Result updateConfig(@RequestParam("file") MultipartFile file ) throws IOException {
        if(file.isEmpty()){
            return new Result(Code.ERROR,"文件为空，上传失败");
        }
        //获取文件名
        String oldFileName=file.getOriginalFilename();
        //获取文件名后缀
        String type = oldFileName.contains(".")?oldFileName.substring(oldFileName.lastIndexOf(".")+1):null;
        String fileType = fileConfig.getFileType();
        //校验文件类型
        if(type != null && fileType.contains(type)){
            //向配置服推送数据
            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(1110);
            msg.setSub(2);
            msg.setBody(ByteString.copyFrom(file.getBytes()));
            boolean b = toolService.sendTo(msg.build(), "http://127.0.0.1:9010");
            if(b){
                return new Result(Code.SUCCESS,"配置更新成功");
            }
            return new Result(Code.ERROR,"配置更新失败");
        }
        return null;
    }

}
