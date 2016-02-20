package com.otter.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otter.author.Author;
import com.otter.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Created by David on 2/20/2016.
 */
@Service
public class BookHelper {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }


    public Book retrieveBook(String isbn) {

        // Check for book already in database, if so return the book
        Book book = bookRepository.findByIsbn(Integer.valueOf(isbn));
        if (book != null) {
            System.out.println("Book already found");
            return book;
        } else {

            // Try to find the book in the API
            RestTemplate restTemplate = new RestTemplate();
            try {
                String url = "http://isbndb.com/api/v2/json/290T9YDA/book/" + isbn;

                String jsonString = restTemplate.getForObject(url, String.class);
                return saveJSON(jsonString);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private Book saveJSON(String jsonString) throws IOException {
        // If error occurs searching for the book (it doesn't exist) error is handled in try catch above
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bookNode = mapper.readTree(jsonString).get("data").get(0);

        Book book = new Book();
        book.setIsbn(bookNode.get("isbn10").asInt());
        book.setTitle(bookNode.get("title").toString());
        book.setPublisher(bookNode.get("publisher_name").toString());
        book.setSummary(bookNode.get("summary").toString());

        JsonNode authorNode = bookNode.get("author_data").get(0);
        Author author = authorRepository.findByName(authorNode.get("name").toString());
        if (author == null) {
            author = new Author(authorNode.get("name").toString());
        }
        author.getBookList().add(book);
        book.setAuthor(author);

        authorRepository.save(author);
        bookRepository.save(book);

        // TODO fix god aweful string errors but else good shit
        return book;
    }
}
