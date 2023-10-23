package com.liu.test;

import com.liu.bean.Book;
import com.liu.dao.BookDao;
import com.liu.dao.Impl.BookDaoImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class BookDaoImplTest {
   BookDao bookDao  = new BookDaoImpl();
   @Test
    public void findAllBook() throws SQLException {
       List<Book> all = bookDao.findAll();
       for (Book book : all) {
           System.out.println("book = " + book);
       }
   }
}
