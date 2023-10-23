package com.liu.service.Impl;

import com.liu.bean.Admin;
import com.liu.dao.AdminDao;
import com.liu.dao.Impl.AdminDaoImpl;
import com.liu.service.AdminService;

import java.sql.SQLException;

public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public Admin login(Admin admin) throws SQLException {
        return adminDao.queryUserByUsernameAndPassword(admin.getUsername(), admin.getPassword());
    }
}
