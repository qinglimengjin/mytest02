package com.liu.dao;

import com.liu.bean.Admin;
import com.liu.utils.BaseInterface;

import java.sql.SQLException;

public interface AdminDao extends BaseInterface<Admin> {
    public Admin queryUserByUsernameAndPassword(String username, String password) throws SQLException;
}
