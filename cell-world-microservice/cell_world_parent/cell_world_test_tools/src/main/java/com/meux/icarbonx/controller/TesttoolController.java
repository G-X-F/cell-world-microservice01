package com.meux.icarbonx.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import com.meux.icarbonx.configuration.CommandConfig;
import com.meux.icarbonx.entities.Code;
import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.proto.PbItem;
import com.meux.icarbonx.proto.ProtobuffFrame;
import com.meux.icarbonx.service.TestToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.meux.icarbonx.utis.Avatas.Array2List;

@RestController
public class TesttoolController {

    @Autowired
    private TestToolService toolService;

    @Autowired
    private CommandConfig config;

    @GetMapping("/item/addItem")
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
            request.setSub(config.getAddItem());
            request.setBody(body.build().toByteString());

            boolean b = toolService.issueItems(request.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"发放成功");
            }
            return new Result(Code.ERROR,"发放失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }


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
            request.setSub(config.getSetLevel());
            request.setBody(body.build().toByteString());

            boolean b = toolService.setRoleLevel(request.build(),config.getUrl());
            if(b){
                return new Result(Code.SUCCESS,"等级设置成功");
            }
            return new Result(Code.ERROR,"等级设置失败");
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return null;
    }

}
