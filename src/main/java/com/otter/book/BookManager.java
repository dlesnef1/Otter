package com.otter.book;

import com.otter.author.Author;
import com.otter.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 2/20/2016.
 */
@Service
public class BookManager {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookHelper bookHelper;

    String API_KEY = "AIzaSyDMBHkUiVJm8LQBQx1x6z4LsjZqGXq4lLI";

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    // Return the top 5 books and let user pick the one that matches
    public List<Book> retrieveBooks(String title) throws UnsupportedEncodingException {
        String searchFor;
        try {
            searchFor = URLEncoder.encode(title, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            searchFor = "";
        }

        String googleSearch = "https://www.googleapis.com/books/v1/volumes?q=" + searchFor + "&key=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        Map map = restTemplate.getForObject(googleSearch, Map.class);

        return bookHelper.parseJsonForBooks(map);
    }

    public Book updateBook(String isbn, String title, String publisher, String publishedDate, String summary, String authorName, Integer timesRead) {
        Book book = bookRepository.findByIsbn(isbn);
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            author = new Author(authorName);
        }

        if (book == null) {
            // We need to make a new book then
            book = new Book(isbn, title, author, publisher, publishedDate, summary);
            book.setTimesRead(timesRead);
            author.getBookList().add(book);
        } else {
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setPublisher(publisher);
            book.setSummary(summary);
            book.setAuthor(author);
            book.setTimesRead(timesRead);
        }

        authorRepository.save(author);
        bookRepository.save(book);
        return book;
    }

    public Book findBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            book = new Book();
        }
        return book;
    }
}
