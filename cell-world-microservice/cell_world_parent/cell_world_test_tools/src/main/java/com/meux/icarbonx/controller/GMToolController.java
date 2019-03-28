package com.meux.icarbonx.controller;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.configuration.MailConfig;
import com.meux.icarbonx.configuration.ServerPropertyConfig;
import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.proto.*;
import com.meux.icarbonx.service.GmToolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import static com.meux.icarbonx.utis.Avatas.Array2List;

@RestController
public class GMToolController {

    private Logger logger = LoggerFactory.getLogger(GMToolController.class);

    private final GmToolService toolService;

    private final MailConfig mailConfig;

    private final ServerPropertyConfig propertyConfigConfig;



    @Autowired
    public GMToolController(GmToolService toolService, MailConfig config, ServerPropertyConfig fileConfig) {
        this.toolService = toolService;
        this.mailConfig = config;
        this.propertyConfigConfig = fileConfig;
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
            request.setCmd(mailConfig.getCmd());
            request.setSub(mailConfig.getSub_03());
            request.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(request.build(), mailConfig.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"发放成功");
            }
            return new Result(Code.ERROR,"发放失败");
        } catch (Exception e) {
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
            request.setCmd(mailConfig.getCmd());
            request.setSub(mailConfig.getSub_04());
            request.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(request.build(), mailConfig.getUrl());
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
            msg.setCmd(mailConfig.getCmd());
            msg.setSub(mailConfig.getSub_01());
            msg.setBody(body.build().toByteString());

            boolean b = toolService.sendTo(msg.build(), mailConfig.getUrl());
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
            msg.setCmd(mailConfig.getCmd());
            msg.setSub(mailConfig.getSub_02());
            msg.setBody(body.build().toByteString());
            boolean b = toolService.sendTo(msg.build(), mailConfig.getUrl());
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
    public Result updateConfig(@RequestParam("wid")Integer wid,@RequestPart(value = "file")MultipartFile[] file){
        logger.info("更新服务器配置");
        try {
            ProtoServerConf.FileUpload.Builder body = ProtoServerConf.FileUpload.newBuilder();
            ProtoServerConf.MultiFile.Builder mFile = ProtoServerConf.MultiFile.newBuilder();
            for(MultipartFile  f: file){
                if(f.isEmpty()) continue;
                mFile.setName(f.getOriginalFilename());
                mFile.setFile(ByteString.copyFrom(f.getBytes()));
                body.addMultiFiles(mFile.build());
                mFile.clear();
            }
            //向配置服推送数据
            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(propertyConfigConfig.getCmd());
            msg.setSub(propertyConfigConfig.getSub());
            msg.setBody(body.build().toByteString());
            String url = propertyConfigConfig.getWordMap().get(wid);
            boolean b = toolService.sendTo(msg.build(), "http://" + url);
            if(b){
                logger.info("更新服务器配置成功");
                return new Result(Code.SUCCESS,"配置更新成功");
            }
            logger.info("更新服务器配置失败");
            return new Result(Code.ERROR,"配置更新失败");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping(value = "/config/test",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result testConfig(@RequestParam("wid") int wid,@RequestPart(value = "file")MultipartFile file){
        try {
            System.out.println("========================MULTIPART============================");
            System.out.println(file.getOriginalFilename());
            String savePath = "D:\\hello/";
            File targetFile = new File(savePath + file.getOriginalFilename());
            if(!targetFile.exists()) targetFile.createNewFile();
            OutputStream out = new FileOutputStream(targetFile);
            out.write(file.getBytes());

            ProtoServerConf.FileUpload.Builder body = ProtoServerConf.FileUpload.newBuilder();

            ProtoServerConf.MultiFile.Builder mFile = ProtoServerConf.MultiFile.newBuilder();
            mFile.setName(file.getOriginalFilename());
            mFile.setFile(ByteString.copyFrom(file.getBytes()));

            body.addMultiFiles(mFile.build());

            ProtobuffFrame.Request.Builder msg = ProtobuffFrame.Request.newBuilder();
            msg.setCmd(1110);
            msg.setSub(2);
            msg.setBody(body.build().toByteString());
            boolean b = toolService.sendTo(msg.build(), "http://127.0.0.1:9010");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
