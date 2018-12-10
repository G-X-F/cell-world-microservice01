package com.meux.icarbonx.dao;

import com.meux.icarbonx.entities.User;

import java.util.List;

//@Mapper
public interface UserDao {
    /**
     * 登陆
     * @return
     */
    User queryAccount(User user);

    /**
     * 添加一个账号
     * @return
     */
    Integer addAccount(User user);

    /**
     * 删除一个账号
     * @param uid
     * @return
     */
    Integer deleteAccount(Integer uid);

    /**
     * 查询所有的账号
     * @return
     */
    List<User> queryAllAccount();

}
