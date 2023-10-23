package com.liu.servlet;

import com.liu.utils.BaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends BaseServlet {
    protected void loginTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if ("ljx".equals(username) && "123456".equals(password)) {
            //登录成功
            Cookie cookie = new Cookie("username", username);
            Cookie passCookie = new Cookie("password", password);
            cookie.setMaxAge(60 * 60);//当前Cookie 一周内有效
            passCookie.setMaxAge(60 * 60);
            response.addCookie(cookie);
            response.addCookie(passCookie);
            response.getWriter().write("登录 成功");
        } else {
            //登录失败
            response.getWriter().write("登录 失败");
        }
    }
}
