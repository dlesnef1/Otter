package com.otter.account;

import com.otter.book.Book;
import com.otter.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 3/14/16.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    public Account find(Account account) {
        return accountRepository.findOne(account.getId());
    }

    public Account create(String name, String password, String confirm) {
        if (!password.equals(confirm)) {
            return null;
        }

        Account account = new Account(name, password);
        return accountRepository.save(account);
    }

    public Account addBook(Account accountIn, String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        Account account = accountRepository.findOne(accountIn.getId());

        if (book != null) {
            if (!account.getBooks().contains(book)) {
                account.getBooks().add(book);
                book.getAccounts().add(account);
                accountRepository.save(account);
            }
        }

        return account;
    }

    public Map<String, Account> login(String name, String password) {
        Account account = accountRepository.findByName(name);
        if (account != null) {
            if (account.getPassword().equals(password)) {
                Map <String, Account> entry = new HashMap<>();
                entry.put(account.getName() + "+" + account.getPassword(), account);
                return entry;
            }
        }
        return null;
    }
}
