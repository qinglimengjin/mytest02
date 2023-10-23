package com.liu.dao.Impl;

import com.liu.bean.Book;
import com.liu.dao.BookDao;
import com.liu.utils.BaseDao;
import com.liu.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class BookDaoImpl extends BaseDao implements BookDao {
    @Override
    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
//        String sql = "select count(*) from t_book where 1 = 1 and name = ? and author = ? and price between ? and ?";
        StringBuilder sql = new StringBuilder("select count(*) from t_book where 1 = 1 ");
        ArrayList lsit = new ArrayList();
        if (name != null && name != "") {
            sql.append(" and name like ?");
            lsit.add("%" + name + "%");
        }
        if (author != null && author != "") {
            sql.append(" and author like ?");
            lsit.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min>max
            if (min.compareTo(max) == 1) {//两值交换
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ?");
            lsit.add(min);
            lsit.add(max);
        } else if ((min != null && min.signum() == 1)) {
            sql.append(" and price > ?");
            lsit.add(min);
        } else if ((max != null && max.signum() == 1)) {
            sql.append(" and price < ?");
            lsit.add(max);
        }

        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql.toString(), handler, lsit.toArray());
        return query.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
//        String sql = " select * from t_book where 1 = 1 and price between ? and ? order by id desc limit ?,?";
        StringBuilder sql = new StringBuilder("select * from t_book where 1 = 1 ");
        ArrayList lsit = new ArrayList();
        if (name != null && name != "") {
            sql.append(" and name like ?");
            lsit.add("%" + name + "%");
        }
        if (author != null && author != "") {
            sql.append(" and author like ?");
            lsit.add("%" + author + "%");
        }
        if ((min != null && min.signum() == 1) && (max != null && max.signum() == 1)) {
            //min>max
            if (min.compareTo(max) == 1) {//两值交换
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ?");
            lsit.add(min);
            lsit.add(max);
        } else if ((min != null && min.signum() == 1)) {
            sql.append(" and price > ?");
            lsit.add(min);
        } else if ((max != null && max.signum() == 1)) {
            sql.append(" and price < ?");
            lsit.add(max);
        }
        String end = " order by id desc limit ?,?";
        sql.append(end);
        lsit.add(begin);
        lsit.add(pageSize);
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class, processor);
        List<Book> bookList = queryRunner.query(sql.toString(), handler, lsit.toArray());
        return bookList;
    }

    @Override
    public Integer queryForPageByPriceCount(Integer min, Integer max) throws SQLException {
        String sql = "select count(*) from t_book where price between ? and ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler, min, max);
        return query.intValue();
    }

    @Override
    public List<Book> queryForPageByPrice(int begin, int pageSize, Integer min, Integer max) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        String sql = " select * from t_book where price between ? and ? order by id desc limit ?,?";
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class, processor);
        List<Book> bookList = queryRunner.query(sql, handler, min, max, begin, pageSize);
        return bookList;
    }

    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler);
        return query.intValue();
    }

    /*
     *
     * 分页查询
     * @param begin 起始值
     * @param pageSize 每次查询几条数据
     * */
    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        String sql = " select * from t_book order by id desc limit ?,?";
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class, processor);
        List<Book> bookList = queryRunner.query(sql, handler, begin, pageSize);
        return bookList;
    }


    @Override
    public List<Book> findAll() throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class, processor);
        List<Book> Books = queryRunner.query("select * from t_book order by id desc", handler);
        return Books;
    }

    @Override
    public void save(Book book) throws SQLException {
        queryRunner.update("insert into t_book values(null,?,?,?,?,?,?)",
                book.getName(), book.getPrice(), book.getAuthor(), book.getSales(),
                book.getStock(), book.getImgPath());
        System.out.println("Insert Successfully!");
    }

    @Override
    public void updateById(Book book) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        queryRunner.update(connection, "update t_book set name=?,price=?,author=?,sales=?,stock=?,img_path=? where id=?",
                book.getName(), book.getPrice(), book.getAuthor(), book.getSales(), book.getStock(),
                book.getImgPath(), book.getId());
        System.out.println("Update Successfully!");
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        queryRunner.update("delete from t_book where id = ?", id);
        System.out.println("Delete Successfully!");
    }

    @Override
    public Book findById(Integer id) throws SQLException {
        GenerousBeanProcessor beanProcessor = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(beanProcessor);
        BeanHandler<Book> bean = new BeanHandler<>(Book.class, processor);
        return queryRunner.query("select * from t_book where id=?", bean, id);
    }

    @Override
    public List<Book> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_book limit ? , ?";
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class);
        List<Book> bookList = queryRunner.query(sql, handler, (pageNumber - 1) * pageSize, pageSize);
        return bookList;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long query = queryRunner.query(sql, handler);
        return query.intValue();
    }
}
