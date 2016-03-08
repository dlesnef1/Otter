package com.otter.book;

import com.otter.author.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by David on 3/7/2016.
 */

@Service
public class BookHelper {

    @SuppressWarnings("unchecked")
    public List<Book> parseJsonForBooks(Map json) {
        List<Book> books = new ArrayList<>();

        List<Map> items = (ArrayList) json.get("items");
        for (int i = 0; i < 5; i++) {
            Map volumeInfo = (Map) items.get(i).getOrDefault("volumeInfo", "");
            Book book = new Book();
            book.setTitle((String) volumeInfo.getOrDefault("title", ""));
            book.setPublisher((String) volumeInfo.getOrDefault("publisher", ""));
            book.setPublishedDate((String) volumeInfo.getOrDefault("publishedDate", ""));
            book.setSummary((String) volumeInfo.getOrDefault("description", ""));

            List<String> authors = (ArrayList) volumeInfo.getOrDefault("authors", new ArrayList<>());
            if (authors.size() > 0) {
                book.setAuthor(new Author(authors.get(0)));
            }

            List<Map> isbns = (ArrayList) volumeInfo.getOrDefault("industryIdentifiers", new ArrayList<>());
            for (Map isbn : isbns) {
                if (isbn.get("type").equals("ISBN_10")) {
                    System.out.println(isbn);
                    book.setIsbn((String) isbn.get("identifier"));
                }
            }
            books.add(book);
        }

        return books;
    }

}
