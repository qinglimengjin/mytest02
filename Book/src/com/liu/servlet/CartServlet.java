package com.liu.servlet;

import com.liu.bean.Book;
import com.liu.bean.CartItem;
import com.liu.service.BookService;
import com.liu.service.Cart;
import com.liu.service.Impl.BookServiceImpl;
import com.liu.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BookServlet {


    /**
     * 加入购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        HttpSession session = request.getSession();
        // 获取请求的参数 商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用bookService.queryBookById(id):Book 得到图书的信息
        try {
            //通过主键id获取图书对象book
            Book book = bookService.queryBookById(id);
            //把图书信息,转换成为CartItem商品项
            CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
            //从session回魂作用域中取出cart,如果为null,则表示购物车中无商品,否则有
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            //添加商品
            session.setAttribute("lastName", book.getName());
            cart.addItem(cartItem);
            System.out.println("cart = " + cart);
            System.out.println("请求头Referer的值" + request.getHeader("Referer"));
            response.sendRedirect(request.getHeader("Referer"));
/*            // 把图书信息，转换成为CartItem 商品项
            CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
            // 调用Cart.addItem(CartItem);添加商品项
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                request.getSession().setAttribute("cart", cart);
            }
            cart.addItem(cartItem);
            System.out.println(cart);
            System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));
            // 重定向回原来商品所在的地址页面
            response.sendRedirect(request.getHeader("Referer"));*/
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除商品项
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void clearItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.clear();
        }
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id, count);
        }
        response.sendRedirect(request.getHeader("Referer"));
    }
}
