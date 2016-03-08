package com.otter.book;

import com.otter.author.Author;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by David on 2/16/2016.
 */
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String isbn;

    @NotNull
    private String title;

    @ManyToOne
    private Author author;

    private String publisher;

    private String publishedDate;

    private String summary;

    private Integer timesRead;

    public Book(String isbn, String title, Author author, String publisher, String publishedDate, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.summary = summary;
        timesRead = 0;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getTimesRead() {
        return timesRead;
    }

    public void setTimesRead(Integer timesRead) {
        this.timesRead = timesRead;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", publisher='" + publisher + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
