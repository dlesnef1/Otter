package com.otter.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by David on 3/14/16.
 */

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account find(Long id) {
        return accountRepository.findOne(id);
    }

    public Account create(String name, String password, String confirm) {
        if (!password.equals(confirm)) {
            return null;
        }

        Account account = new Account(name, password);
        return accountRepository.save(account);
    }
}
