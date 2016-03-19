package com.otter.account;

import com.otter.book.Book;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 3/14/16.
 */

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="account_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @ManyToMany(cascade= CascadeType.ALL, mappedBy="accounts")
    private List<Book> books;

    public Account() {
    }

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
        this.books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
