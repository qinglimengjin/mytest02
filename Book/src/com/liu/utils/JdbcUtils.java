package com.liu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("all")
public class JdbcUtils {
    //数据源
    private static DruidDataSource dataSource;
    //本地线程
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            Properties properties = new Properties();
            // 读取 jdbc.properties 属性配置文件
            InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            // 从流中加载数据
            properties.load(inputStream);
            // 创建 数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池中的连接
     *
     * @return 如果返回null, 说明获取连接失败<br />有值就是获取连接成功
     */
    public static Connection getConnection() {
        //从本地线程中获取Connection对象
        Connection conn = threadLocal.get();
        //如果conn对象是null就说明本地线程中并没有存储conn对象
        if (conn == null) {
            try {
                conn = dataSource.getConnection();//从数据库连接池中获取连接
                threadLocal.set(conn); // 保存到ThreadLocal 对象中，供后面的jdbc 操作使用
                conn.setAutoCommit(false); // 设置为手动管理事务
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 提交事务，并关闭释放连接
     */
    public static void commitAndClose() {
        Connection connection = threadLocal.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.commit(); // 提交 事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
// 一定要执行remove 操作，否则就会出错。（因为Tomcat 服务器底层使用了线程池技术）
        threadLocal.remove();
    }

    /**
     * 回滚事务，并关闭释放连接
     */
    public static void rollbackAndClose() {
        Connection connection = threadLocal.get();
        if (connection != null) { // 如果不等于null，说明 之前使用过连接，操作过数据库
            try {
                connection.rollback();//回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close(); // 关闭连接，资源资源
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
// 一定要执行remove 操作，否则就会出错。（因为Tomcat 服务器底层使用了线程池技术）
        threadLocal.remove();
    }

    /**
     * 关闭连接，放回数据库连接池
     *
     * @param conn

    public static void close(Connection conn) {
    if (conn != null) {
    try {
    conn.close();
    } catch (SQLException e) {
    e.printStackTrace();
    }
    }
    }*/
}