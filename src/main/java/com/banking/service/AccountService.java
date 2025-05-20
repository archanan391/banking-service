package com.banking.service;

import com.banking.dto.TransactionResponse;
import com.banking.exception.AccountNotFoundException;
import com.banking.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service @Slf4j
public class AccountService {

    Map<String, Account> accounts = new HashMap<>();

    public TransactionResponse create(String accountType, String userID, Double balance){
        Account account = new Account(UUID.randomUUID().toString(), balance, accountType, userID);
        accounts.put(account.getAccountID(), account);
        log.debug("Account Creation Successful");
        return new TransactionResponse(account.getAccountID(), account.getBalance(), "Account Creation Successful");
    }

    public TransactionResponse deposit(String accountID, Double amount){
        Account account = accounts.computeIfPresent(accountID, (key, acc) -> {
            acc.setBalance(acc.getBalance() + amount);
            return acc;
        });
        if(account != null) {
            log.debug("Deposit Successful");
            return new TransactionResponse(accountID, account.getBalance(), "Deposit successful");
        }
        else{
            log.warn("Account not present");
            throw new AccountNotFoundException(accountID);
        }
    }

    public TransactionResponse withdraw(String accountID, Double amount){
        Account account = accounts.get(accountID);
        if(account == null){
            log.warn("Account not found");
            throw new AccountNotFoundException(accountID);
        }
        if(account.getBalance() < amount){
            log.warn("Insufficient funds available");
            return new TransactionResponse(accountID, account.getBalance(), "Insufficient funds available");
        }
        account.setBalance(account.getBalance() - amount);
        log.debug("Withdraw successful");
        return new TransactionResponse(accountID, account.getBalance(), "Withdraw successful");
    }

    public TransactionResponse checkBalance(String accountID){
        Account account = accounts.getOrDefault(accountID, null);
        if(account == null){
            log.warn("Account not found");
            throw new AccountNotFoundException(accountID);
        }
        return new TransactionResponse(accountID, account.getBalance(), "Check balance successful");
    }


}
