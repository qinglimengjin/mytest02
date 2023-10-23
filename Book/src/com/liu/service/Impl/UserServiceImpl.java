package com.liu.service.Impl;

import com.liu.bean.User;
import com.liu.dao.Impl.UserDaoImpl;
import com.liu.dao.UserDao;
import com.liu.service.UserService;

import java.sql.SQLException;
//服务层[业务层]
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) throws SQLException {
        userDao.save(user);
    }

    @Override
    public User login(User user) throws SQLException {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) throws SQLException {
        User user = userDao.queryUserByUsername(username);
        return user != null;
    }
}
