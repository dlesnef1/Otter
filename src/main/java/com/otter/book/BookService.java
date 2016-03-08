package com.otter.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@Service
public class BookService {

    @Autowired
    BookManager bookManager;

    public List<Book> findAll() {
        return bookManager.findAll();
    }

    public List<Book> searchFor(String title) throws UnsupportedEncodingException {
        return bookManager.retrieveBooks(title);
    }

    public Book updateBook(String isbn, String title, String publisher, String publishedDate, String summary, String author, Integer timesRead) {
        return bookManager.updateBook(isbn, title, publisher, publishedDate, summary, author, timesRead);
    }

    public Book findBook(String isbn) {
        return bookManager.findBook(isbn);
    }
}
