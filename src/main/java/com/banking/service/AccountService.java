package com.banking.service;

import com.banking.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service @Slf4j
public class AccountService {

    Map<String, Account> accounts = new HashMap<>();

    public void deposit(String accountID, Double amount){
        Account account = accounts.computeIfAbsent(accountID, key -> new Account(accountID, 0));
        account.setBalance(account.getBalance() + amount);
        log.info("Money deposited into the account");
    }

    public void withdraw(String accountID, Double amount){
        Account account = accounts.getOrDefault(accountID, null);
        if(account != null && account.getBalance() > amount){
            account.setBalance(account.getBalance() - amount);
            log.info("Withdraw successful");
        }
        log.warn("Insufficient funds available");
    }

    public Optional<Double> checkBalance(String accountID){
        Account account = accounts.getOrDefault(accountID, null);
        if(account != null){
            return Optional.ofNullable(account.getBalance());
        }
        return Optional.empty();
    }

}
