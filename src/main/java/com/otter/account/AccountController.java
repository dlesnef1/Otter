package com.otter.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 3/14/16.
 */

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private Map<String, Account> tokens = new HashMap<>();

    @RequestMapping
    public Account find(@RequestHeader(value = "token") String token) {
        if (tokens.containsKey(token)) {
            return accountService.find(tokens.get(token));
        }
        return null;
    }

    // TODO should probably log them in as wel henya?
    @RequestMapping(method = RequestMethod.POST)
    public Account create(@RequestParam(value = "name") String name,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "confirmation") String confirmation) {
        return accountService.create(name, password, confirmation);
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public Account addBook(@RequestHeader (value = "token") String token,
                           @RequestParam(value = "isbn") String isbn) {
        if (tokens.containsKey(token)) {
            return accountService.addBook(tokens.get(token), isbn);
        }
        return null;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "name") String name,
                        @RequestParam(value = "password") String password) {
        Map<String, Account> entry = accountService.login(name, password);

        if (entry != null) {
            tokens.putAll(entry);
            return entry.keySet().iterator().next();
        }
        return "No matching account data";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(@RequestHeader(value = "token") String token) {
        if (tokens.containsKey(token)) {
            tokens.remove(token);
            return "Successfully logged out!";
        }
        return "User not found";
    }
}
