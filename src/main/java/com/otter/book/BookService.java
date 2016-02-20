package com.otter.book;

import com.otter.author.Author;
import com.otter.author.AuthorRepository;
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
}
