package com.liu.dao.Impl;

import com.liu.bean.Order;
import com.liu.dao.OrderDao;
import com.liu.utils.BaseDao;
import com.liu.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public List<Order> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        queryRunner.update(connection, "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)",
                order.getOrderId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getUserId());
        System.out.println("Insert Successfully!");
    }

    @Override
    public void updateById(Order order) throws SQLException {

    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Order findById(Integer id) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        BeanHandler<Order> bean = new BeanHandler<>(Order.class, processor);
        return queryRunner.query("select * from t_book where id=?", bean, id);
    }

    @Override
    public List<Order> page(Integer pageNumber) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        String sql = "select * from t_order order by create_time desc limit ?,?";
        return queryRunner.query(sql, new BeanListHandler<>(Order.class, processor),
                (pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_order";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler);
        return query.intValue();
    }
}