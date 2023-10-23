package com.liu.test;

import com.liu.bean.User;
import com.liu.dao.Impl.UserDaoImpl;
import com.liu.dao.UserDao;
import org.junit.Test;

import java.sql.SQLException;


public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();
    @Test
    public void queryUserByUsername() {
        try {
            User user= userDao.queryUserByUsername("liujixin");
            System.out.println("user = " + user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void queryUserByUsernameAndPassword() {
        try {
            User user= userDao.queryUserByUsernameAndPassword("admin","admin");
            System.out.println(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void save() {
        User user = new User(null, "liujixin", "123456", "123@qq.com");
        try {
            userDao.save(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void findAll() {
    }

    @Test
    public void updateById() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void page() {
    }

    @Test
    public void pageRecord() {
    }
}