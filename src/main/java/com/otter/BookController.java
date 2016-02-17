package com.otter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @RequestMapping
    public List<Book> all(){
        return bookService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void one(@RequestParam("isbn") Integer isbn, HttpServletResponse response) throws IOException {
        bookService.addBook(isbn);
        response.sendRedirect("/book");
    }
}
