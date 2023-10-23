package com.liu.service.Impl;

import com.liu.bean.Book;
import com.liu.dao.BookDao;
import com.liu.dao.Impl.BookDaoImpl;
import com.liu.service.BookService;
import com.liu.utils.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) throws SQLException {
        bookDao.save(book);
    }

    @Override
    public void deleteBookById(Integer id) throws SQLException {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        bookDao.updateById(book);
    }

    @Override
    public Book queryBookById(Integer id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> queryBooks() throws SQLException {

        List<Book> bookList = bookDao.findAll();
        return bookList;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) throws SQLException {
        //需要queryForPageTotalCount()
        // 和queryForPageItems()方法
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount();//获取总记录数
        page.setPageTotalCount(totalCount);//设置总记录数
        page.setPageTotal((totalCount + pageSize - 1) / pageSize);//设置总页数;
        page.setPageNo(pageNo);//设置当前页
        page.setItems(bookDao.queryForPageItems((page.getPageNo() - 1) * pageSize, pageSize));//设置分页查询的结果集
        return page;
    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, Integer min, Integer max) throws SQLException {
        Page<Book> page = new Page<>();
        Integer priceCount = bookDao.queryForPageByPriceCount(min, max);
        page.setPageTotalCount(priceCount);
        page.setPageTotal((priceCount + pageSize - 1) / pageSize);//设置总页数;
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageByPrice((page.getPageNo() - 1) * pageSize, pageSize, min, max));
        return page;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        Page<Book> page = new Page<>();
        Integer priceCount = bookDao.queryForPageTotalCount(name,author,min, max);
        page.setPageTotalCount(priceCount);
        page.setPageTotal((priceCount + pageSize - 1) / pageSize);//设置总页数;
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageItems((page.getPageNo() - 1) * pageSize, pageSize,name,author, min, max));
        return page;
    }
}
