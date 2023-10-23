package com.liu.test;

import com.liu.bean.User;
import com.liu.service.Impl.UserServiceImpl;
import com.liu.service.UserService;
import org.junit.Test;

import java.sql.SQLException;
@SuppressWarnings("all")
public class UserServiceTest {
    UserService userService = new UserServiceImpl();

    @Test
    public void registUser() {
        try {
            userService.registUser(new User(null, "bbj168", "666666", "bbj168@qq.com"));
            userService.registUser(new User(null, "abc168", "666666", "abc168@qq.com"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void login() {
        try {
            System.out.println(userService.login(new User(null, "liujixin", "123456", null)));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void existsUsername() {
        try {
            if (userService.existsUsername("liujixin")) {
                System.out.println("用户名已存在！");
            } else {
                System.out.println("用户名可用！");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}