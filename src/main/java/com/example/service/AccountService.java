package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) throws AuthenticationException {
        String username = account.getUsername();
        String password = account.getPassword();

        if(username == null || username.length() == 0 || password.length() < 4) {
            throw new AuthenticationException("Invalid username or password");
        }

        Optional<Account> oldAccount = accountRepository.findByUsername(username);
        
        if(oldAccount.isPresent()) {
            return null;
        }

        return (Account) accountRepository.save(account);
    }

    public Account login(Account account) throws AuthenticationException {
        Optional<Account> userAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(userAccount.isPresent()) {
            return userAccount.get();
        }

        return null;
    }
}
