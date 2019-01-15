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
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
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

    /**
     * 文件上传
     */

    @PostMapping(value = "/config/update",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateConfig(@RequestParam("wid")int wid,@RequestPart(value = "file")MultipartFile[] file){
        try {
            ProtoServerConf.FileUpload.Builder body = ProtoServerConf.FileUpload.newBuilder();
            for(MultipartFile  f: file){
                if(f.isEmpty()) continue;
                String savePath = "D:\\hello"+"/";
                f.transferTo(new File(savePath + f.getOriginalFilename() ));

                body.addFile(ByteString.copyFrom(f.getBytes()));
            }
            //向配置服推送数据
            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(1110);
            msg.setSub(2);
            msg.setBody(body.build().toByteString());
            boolean b = toolService.sendTo(msg.build(), "http://127.0.0.1:9010");
            if(b){
                return new Result(Code.SUCCESS,"配置更新成功");
            }
            return new Result(Code.ERROR,"配置更新失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping(value = "/config/test",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result testConfig(@RequestParam("wid") int wid,@RequestPart(value = "file")MultipartFile file){
        try {
            System.out.println("========================MULTIPART============================");
            System.out.println(file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
