package com.liu.dao;

import com.liu.bean.User;
import com.liu.utils.BaseInterface;

import java.sql.SQLException;

public interface UserDao extends BaseInterface<User> {

    public User queryUserByUsername(String username) throws SQLException;

    /**
     * 根据 用户名和密码查询用户信息
     *
     * @param username
     * @param password
     * @return 如果返回null, 说明用户名或密码错误, 反之亦然
     */
    public User queryUserByUsernameAndPassword(String username, String password) throws SQLException;

    /**
     *保存用户信息
     *@param user
     *@return 返回-1 表示操作失败，其他是sql 语句影响的行数
     */
}
