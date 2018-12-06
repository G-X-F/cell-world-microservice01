package com.meux.icarbonx.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.configuration.CommandConfig;
import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.proto.*;
import com.meux.icarbonx.service.TestToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

import static com.meux.icarbonx.utis.Avatas.Array2List;

@RestController
public class GMToolController {

    @Autowired
    private TestToolService toolService;

    @Autowired
    private CommandConfig config;


    /**
     * 发放道具
     * @param rid
     * @param wid
     * @param items
     * @param nums
     * @return
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
            request.setSub(config.getSub_04());
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
     * @param rid
     * @param wid
     * @param level
     * @return
     */
    @GetMapping("/item/setlevel")
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
            request.setSub(config.getSub_05());
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
     * @param wid
     * @param tempId
     * @return
     */
    @GetMapping("/item/sysmail")
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
     * @param rid
     * @param wid
     * @param tempId
     * @return
     */
    @GetMapping("/item/patchmail")
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

    @GetMapping("/test")
    public Result test(){
        return new Result(1,"hello");
    }

}
