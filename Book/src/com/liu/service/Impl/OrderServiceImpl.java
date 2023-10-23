package com.liu.service.Impl;

import com.liu.bean.Book;
import com.liu.bean.CartItem;
import com.liu.bean.Order;
import com.liu.bean.OrderItem;
import com.liu.dao.BookDao;
import com.liu.dao.Impl.BookDaoImpl;
import com.liu.dao.Impl.OrderDaoImpl;
import com.liu.dao.Impl.OrderItemDaoImpl;
import com.liu.dao.OrderDao;
import com.liu.dao.OrderItemDao;
import com.liu.service.Cart;
import com.liu.service.OrderService;
import com.liu.utils.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class OrderServiceImpl implements OrderService {
    //订单dao
    private OrderDao orderDao = new OrderDaoImpl();
    //订单项dao
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    private BookDao bookDao = new BookDaoImpl();

    /**
     * 生成一个订单
     * 1.添加一个订单数据到数据库的order表
     * 2.此订单中至少一个订单项,至多有n个,所有要将订单项都添加到orderItem表中
     * 3.清空掉购物车中的数据
     *
     * @param cart   购物车对象
     * @param userId 用户id
     * @return 返回订单的id
     */
    @Override
    public String createOrder(Cart cart, Integer userId) throws SQLException {
        //1,添加一个订单数据到数据库中的order表中
        String orderid = "" + System.currentTimeMillis() + userId;
        Order order = new Order();
        order.setOrderId(orderid);//生成的订单编号
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));//当前系统时间
        order.setPrice(cart.getTotalPrice());//订单的总价格
        order.setStatus(0);//设置订单状态,0表示未发货
        order.setUserId(userId);//设置用户编号,因为这个订单需要知道是谁下的单
        orderDao.save(order);

        //2.此订单中至少一个订单项,至多有n个,所有要将订单项都添加到orderItem表中
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            OrderItem item = new OrderItem();
            item.setName(entry.getValue().getName());//设置订单项的名字
            item.setCount(entry.getValue().getCount());//设置订单项的数量
            item.setPrice(entry.getValue().getPrice());//设置订单项的单价
            item.setTotalPrice(entry.getValue().getTotalPrice());//设置订单项的总价
            item.setOrderId(orderid);//设置订单项的外键id订单号
            orderItemDao.save(item);
            //更新库存和销量
            Book book = bookDao.findById(entry.getValue().getId());//通过图书的主键id查询返回的图书对象
            book.setSales(book.getSales() + entry.getValue().getCount());//设置销量
            book.setStock(book.getStock() - entry.getValue().getCount());//设置库存
            bookDao.updateById(book);//修改销量与库存
        }

        //3.清空购物车
        cart.clear();
        return orderid;
    }

    @Override
    public Page<Order> page(Integer pageNo, Integer pageSize) throws SQLException {
        Page<Order> page = new Page<>();
        Integer totalCOunt = orderDao.pageRecord();
        page.setPageTotalCount(totalCOunt);
        page.setPageTotal((totalCOunt + pageSize - 1) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(orderDao.page(page.getPageNo()));
        return page;
    }

    @Override
    public Page<OrderItem> pageOrder(String orderId, Integer pageNo, Integer pageSize) throws SQLException {
        Page<OrderItem> page = new Page<>();
        Integer totalCOunt = orderItemDao.pageRecord(orderId);
        page.setPageTotalCount(totalCOunt);
        page.setPageTotal((totalCOunt + pageSize - 1) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(orderItemDao.page(orderId, page.getPageNo()));
        return page;
    }
}
