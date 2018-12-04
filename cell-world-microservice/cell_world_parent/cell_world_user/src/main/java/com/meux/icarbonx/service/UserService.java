package com.meux.icarbonx.service;

import com.meux.icarbonx.dao.UserDao;
import com.meux.icarbonx.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登陆
     * @return
     */
    public User queryAccount(User user){

        return userDao.queryAccount(user);
    }

    /**
     * 添加账号
     * @return
     */
    public boolean addAccount(User user) throws IOException {

        return userDao.addAccount(user)==1;
    }

    /**
     * 删除账号
     * @param uid
     * @return
     */
    public boolean deleteAccount(Integer uid){
        return userDao.deleteAccount(uid)==1;
    }

    /**
     * 查询所有的账号
     * @return
     */
    public List<User> queryAllAccount(){
        return userDao.queryAllAccount();
    }

}
