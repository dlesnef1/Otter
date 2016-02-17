package com.otter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by David on 2/16/2016.
 */
@RestController
@RequestMapping("/book")
public class BookController {

    public List<Book> all(){
        return null;
    }

}
