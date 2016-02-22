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
                return saveBook(jsonString);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private Book saveBook(String jsonString) throws IOException{
        Book book = new Book();
        book.setIsbn(Integer.valueOf(getStringFromJson(jsonString, "isbn10")));

        book.setTitle(getStringFromJson(jsonString, "title"));
        book.setPublisher(getStringFromJson(jsonString, "publisher_name"));
        book.setSummary(getStringFromJson(jsonString, "summary"));

        Author author = getAuthor(jsonString);

        author.getBookList().add(book);
        book.setAuthor(author);

        authorRepository.save(author);
        bookRepository.save(book);

        return book;
    }

    // These two methods should be combined somehow. Since they're private I care slightly less though
    private String getStringFromJson(String jsonString, String attribute) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bookNode = mapper.readTree(jsonString).get("data").get(0);

        return bookNode.get(attribute).toString().replace("\"", "");
    }

    // The second method as mentioned above
    private Author getAuthor(String jsonString) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bookNode = mapper.readTree(jsonString).get("data").get(0);
        JsonNode authorNode = bookNode.get("author_data").get(0);

        Author author = authorRepository.findByName(authorNode.get("name").toString().replace("\"", ""));
        System.out.println("Author: " + author);
        System.out.println("All authors: " + authorRepository.findAll());
        if (author == null) {
            author = new Author(authorNode.get("name").toString().replace("\"", ""));
        }
        return author;
    }
}
