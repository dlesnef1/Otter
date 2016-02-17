package com.otter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public void addBook(Integer isbn) {
        if (bookRepository.findByIsbn(isbn) != null) {
            System.out.println("Book already found");
        } else {
            Author author = new Author("Michael Crichton");
            Book book = new Book(isbn, "Jurassic Park", author, "New York", "Dinosaurs and shit");
            author.getBookList().add(book);

            authorRepository.save(author);
            bookRepository.save(book);

            System.out.println("Added new book (and author)");
        }
    }
}
