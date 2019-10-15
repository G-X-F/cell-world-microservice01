package com.meux.icarbonx.controller;

import com.meux.icarbonx.entities.Result;
import com.meux.icarbonx.entities.User;
import com.meux.icarbonx.service.GmToolsFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/item")
@CrossOrigin(value = "*", allowCredentials = "true")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final GmToolsFeignService gmToolsService;


    @Autowired
    public ItemController(GmToolsFeignService gmToolsService) {
        this.gmToolsService = gmToolsService;
    }

    @PostMapping("/additem")
    public Result issueItems(HttpServletRequest request,String rid, int wid, String items, String nums) {
        User account = (User)request.getSession().getAttribute("account");
        if(account == null){
            return null;
        }
        logger.info(account.getUsername() + "进行了添加物品操作，物品ID："+ items + ",物品数量："+ nums);
        //将角色id进行进制转换
        long role = Long.parseLong(rid, 16);

        StringBuilder builder = new StringBuilder();

        String[] split = items.split(",");
        for (String str : split) {
            if (str.startsWith("3") && str.length() <= 7) {//装备如果没有指定属性就是随机属性
                str = str + "00";
            }
            if (builder.length() == 0) {
                builder.append(str);
            } else {
                builder.append(",");
                builder.append(str);
            }
        }
        return gmToolsService.issueItems(role, wid, builder.toString(), nums);
    }

    /**
     * 发送系统邮件
     */
    @PostMapping(value = "sysmail")
    public Result sendSysMail(HttpServletRequest request,int wid, int tempId) {
        logger.info("发送系统邮件");
        User account = (User)request.getSession().getAttribute("account");
        if(account == null){
            logger.info("account 为null");
            return null;
        }
        logger.info(account.getUsername() + "进行了发送系统邮件操作，邮件ID："+ tempId + ",世界ID："+ wid);
        return gmToolsService.sendSysMail(wid, tempId);
    }

    /**
     * 发送定向邮件
     */
    @PostMapping(value = "patchmail")
    public Result sendPatchMail(HttpServletRequest request,String rid, int wid, int tempId) {
        long role = Long.parseLong(rid, 16);
        User account = (User)request.getSession().getAttribute("account");
        if(account == null)return null;
        logger.info(account.getUsername() + "进行了发送指定邮件操作，邮件ID："+ tempId + ",世界ID："+ wid + "收件人：" + role);
        return gmToolsService.sendPatchMail(role, wid, tempId);
    }


}
