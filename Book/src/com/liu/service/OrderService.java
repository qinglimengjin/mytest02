package com.liu.service;

import com.liu.bean.Order;
import com.liu.bean.OrderItem;
import com.liu.utils.Page;

import java.sql.SQLException;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId) throws SQLException;

    public Page<Order> page(Integer pageNo, Integer pageSize) throws SQLException;

    public Page<OrderItem> pageOrder(String orderId, Integer pageNo, Integer pageSize) throws SQLException;
}