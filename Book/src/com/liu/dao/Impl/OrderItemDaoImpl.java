package com.liu.dao.Impl;

import com.liu.bean.OrderItem;
import com.liu.dao.OrderItemDao;
import com.liu.utils.BaseDao;
import com.liu.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

    @Override
    public List<OrderItem> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(OrderItem orderItem) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        queryRunner.update(connection, sql, orderItem.getName(), orderItem.getCount(), orderItem.getPrice(),
                orderItem.getTotalPrice(), orderItem.getOrderId());
        System.out.println("Insert Successfully!");
    }

    @Override
    public void updateById(OrderItem orderItem) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public OrderItem findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<OrderItem> page(Integer pageNumber) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        String sql = "select * from t_order_item where order by id desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<>(OrderItem.class, processor),
                (pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    public List<OrderItem> page(String orderId, Integer pageNumber) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        String sql = "select * from t_order_item where order_id= ? order by id desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<>(OrderItem.class, processor),
                orderId, (pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    public Integer pageRecord() throws SQLException {
        return null;
    }

    @Override
    public Integer pageRecord(String orderId) throws SQLException {
        String sql = "select count(*) from t_order_item where order_id= ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler, orderId);
        return query.intValue();
    }
}
