package com.liu.service;

import com.liu.bean.Admin;

import java.sql.SQLException;

public interface AdminService {
    public Admin login(Admin admin)throws SQLException;
}
