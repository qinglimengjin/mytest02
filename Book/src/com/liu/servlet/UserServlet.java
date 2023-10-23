package com.liu.servlet;

import com.liu.bean.Admin;
import com.liu.bean.User;
import com.liu.service.AdminService;
import com.liu.service.Impl.AdminServiceImpl;
import com.liu.service.Impl.UserServiceImpl;
import com.liu.service.UserService;
import com.liu.utils.BaseServlet;
import com.liu.utils.CookieUtils;
import com.liu.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.sql.SQLException;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@SuppressWarnings("all")
@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
/*      String username = request.getParameter("username");
        String password = request.getParameter("password");*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);

        try {
            User myuser = userService.login(user);
            if (myuser != null) {
                Cookie nameCookie = new Cookie("username", myuser.getUsername());
                Cookie passCookie = new Cookie("password", myuser.getPassword());
                nameCookie.setMaxAge(60 * 60 * 24 * 7);
                passCookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(nameCookie);
                response.addCookie(passCookie);

                HttpSession session = request.getSession();
                session.setAttribute("user", myuser);
                request.setAttribute("msg", "欢迎回来");

                if (request.getParameter("liuurl") != null && !request.getParameter("liuurl").equals("")) {
                    request.getRequestDispatcher(request.getParameter("liuurl")).forward(request, response);
                } else {
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "账号名或登录密码不正确!");
                request.setAttribute("username", user.getUsername());
                request.setAttribute("password", user.getPassword());
                request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void adminlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminServiceImpl();
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Admin admin = new Admin();
        WebUtils.copyParamToBean(parameterMap, admin);
        try {
            Admin myadmin = adminService.login(admin);
            if (myadmin != null) {
                request.getRequestDispatcher("/pages/manager/manager.jsp").forward(request, response);
            } else {
                request.setAttribute("msg", "账号名或登录密码不正确!");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("email", email);*/
        // 获取Session 中的验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap, user);
        String code = request.getParameter("code");//验证码
        System.out.println("code = " + code);
        System.out.println("token = " + token);
        request.setAttribute("code", code);
        request.setAttribute("u", user);//为了回显
        try {
            if (token.equalsIgnoreCase(code)) {
                if (!userService.existsUsername(user.getUsername())) {
                    userService.registUser(user);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    request.setAttribute("msg", "注册成功");
                    request.getRequestDispatcher("/pages/user/success.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "用户名已经存在请更换!");
                    request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg", "验证码不正确!");
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();//session立刻失效
        Cookie nameCookie = CookieUtils.findCookie("username", request.getCookies());
        Cookie passCookie = CookieUtils.findCookie("password", request.getCookies());
        if (nameCookie != null) {
            nameCookie.setMaxAge(0);//立刻失效
            response.addCookie(nameCookie);
        }
        if (passCookie != null) {
            passCookie.setMaxAge(0);//立刻失效
            response.addCookie(passCookie);
        }
        response.sendRedirect("index.jsp");
    }
}
