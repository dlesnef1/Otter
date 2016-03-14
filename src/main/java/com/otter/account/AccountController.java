package com.otter.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by David on 3/14/16.
 */

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/{id}")
    public Account find(@PathVariable Long id) {
        return accountService.find(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Account create(@RequestParam(value = "name", required = false) String name,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "confirmation", required = false) String confirmation) {
        return accountService.create(name, password, confirmation);
    }
}
