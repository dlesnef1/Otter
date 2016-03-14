package com.otter.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@RequestMapping("/books")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping
    public List<Book> all() {
        return bookService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<Book> searchFor(@RequestParam(value = "title") String title) throws IOException {
        return bookService.searchFor(title);
    }

    @RequestMapping("/{isbn}")
    public Book find(@PathVariable String isbn) {
        return bookService.findBook(isbn);
    }

    @RequestMapping(path = "/{isbn}", method = RequestMethod.PUT)
    public Book put(@PathVariable String isbn,
                    @RequestParam(value = "title", required = false) String title,
                    @RequestParam(value = "publisher", required = false) String publisher,
                    @RequestParam(value = "publishedDate", required = false) String publishedDate,
                    @RequestParam(value = "summary", required = false) String summary,
                    @RequestParam(value = "author", required = false) String author,
                    @RequestParam(value = "timesRead", required = false) Integer timesRead) {

        return bookService.updateBook(isbn, title, publisher, publishedDate, summary, author, timesRead);
    }
}
