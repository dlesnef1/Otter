package com.otter.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@Service
public class BookService {

    @Autowired
    BookHelper bookHelper;

    public List<Book> findAll() {
        return bookHelper.findAll();
    }

    public Book addBook(String isbn) {
        return bookHelper.retrieveBook(isbn);
    }

    public Book updateBook(String isbn, String title, String publisher, String summary, String author, Integer timesRead) {
        return bookHelper.updateBook(isbn, title, publisher, summary, author, timesRead);
    }

    public Book findBook(String isbn) {
        return bookHelper.findBook(isbn);
    }
}
