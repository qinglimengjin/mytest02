package com.liu.dao;

import com.liu.bean.Book;
import com.liu.utils.BaseInterface;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BookDao extends BaseInterface<Book> {
    public Integer queryForPageTotalCount() throws SQLException;

    public List<Book> queryForPageItems(int begin, int pageSize) throws SQLException;

    public Integer queryForPageByPriceCount(Integer min, Integer max) throws SQLException;

    public List<Book> queryForPageByPrice(int begin, int size, Integer min, Integer max) throws SQLException;

    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException;

    public List<Book> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException;
}