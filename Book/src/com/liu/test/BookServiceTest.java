package com.liu.test;

import com.liu.bean.Book;
import com.liu.service.BookService;
import com.liu.service.Impl.BookServiceImpl;
import com.liu.utils.Page;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class BookServiceTest {
    BookService bookService = new BookServiceImpl();
    @Test
    public void pageBook() throws SQLException {
        Page<Book> page = bookService.page(1, 6);
        List<Book> items = page.getItems();
        for (Book item : items) {
            System.out.println(item);
        }
    }
}
