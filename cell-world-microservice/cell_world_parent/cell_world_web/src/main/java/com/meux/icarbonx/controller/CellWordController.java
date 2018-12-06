package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.service.GmToolsFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class CellWordController {

    @Autowired
    private GmToolsFeignService gmToolsService;

    @GetMapping("/additem")
    public Result issueItems(String rid,int wid,String items,String nums){
        //将角色id进行进制转换
        long role = Long.parseLong(rid,16);

        StringBuilder builder = new StringBuilder();

        String[] split = items.split(",");
        for(String str : split){
            if(str.startsWith("3") && str.length()<=7){//装备如果没有指定属性就是随机属性
                str = str+"00";
            }
            if(builder.length()==0){
                builder.append(str);
            }else {
                builder.append(",");
                builder.append(str);
            }
        }
        return gmToolsService.issueItems(role,wid,builder.toString(),nums);
    }

    /**
     * 发送系统邮件
     * @param wid
     * @param tempId
     * @return
     */
    public Result sendSysMail(int wid,int tempId){
        return gmToolsService.sendSysMail(wid,tempId);
    }

    /**
     * 发送定向邮件
     * @param rid
     * @param wid
     * @param tempId
     * @return
     */
    public Result sendPatchMail(String rid,int wid,int tempId){
        long role = Long.parseLong(rid,16);
        return gmToolsService.sendPatchMail(role,wid,tempId);
    }

    /**
     * 设置人物等级
     * @param rid
     * @param wid
     * @param level
     * @return
     */
    public Result setRoleLevel(String rid,int wid,int level){
        long role = Long.parseLong(rid,16);
        return gmToolsService.setRoleLevel(role,wid,level);
    }

}
