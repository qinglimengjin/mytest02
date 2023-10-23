package com.liu.dao;

import com.liu.bean.OrderItem;
import com.liu.utils.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDao extends BaseInterface<OrderItem> {
    public Integer pageRecord(String orderId) throws SQLException;

    public List<OrderItem> page(String orderId, Integer pageNumber) throws SQLException;
}
