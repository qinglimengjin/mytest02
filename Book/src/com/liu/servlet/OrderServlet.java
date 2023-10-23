package com.liu.servlet;

import com.liu.bean.Order;
import com.liu.bean.OrderItem;
import com.liu.bean.User;
import com.liu.service.Cart;
import com.liu.service.Impl.OrderServiceImpl;
import com.liu.service.OrderService;
import com.liu.utils.BaseServlet;
import com.liu.utils.Page;
import com.liu.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@SuppressWarnings("all")
@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    //生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "登录超时请重新登录");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);
        }
        try {
            //生成订单并且返回订单号
            String orderId = orderService.createOrder(cart, user.getId());
            //使用重定向防止重复提交,重定向跳转前面不要加/,加了/会变成http//8080/pages/cart/checkout.jsp
            session.setAttribute("user", user);
            session.setAttribute("orderId", orderId);
            response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void lastOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取user信息并上传到下一个session中
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", user);

        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        try {
            Page<Order> page = orderService.page(pageNo, pageSize);
            page.setUrl("OrderServlet?action=lastOrder&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void findOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", user);

        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        String orderId = request.getParameter("orderId");
        try {
            Page<OrderItem> page = orderService.pageOrder(orderId, pageNo, pageSize);
            page.setUrl("OrderServlet?action=findOrderId&orderId=" + orderId + "&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/order/detail.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
