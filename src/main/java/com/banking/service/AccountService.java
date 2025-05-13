package com.banking.service;

import com.banking.dto.TransactionResponse;
import com.banking.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service @Slf4j
public class AccountService {

    Map<String, Account> accounts = new HashMap<>();

    public TransactionResponse deposit(String accountID, Double amount){
        Account account = accounts.computeIfAbsent(accountID, key -> new Account(accountID, 0));
        account.setBalance(account.getBalance() + amount);
        log.debug("Deposit Successful");
        return new TransactionResponse(accountID, account.getBalance(), "Deposit successful");
    }

    public TransactionResponse withdraw(String accountID, Double amount){
        Account account = accounts.getOrDefault(accountID, null);
        if(account != null && account.getBalance() > amount){
            account.setBalance(account.getBalance() - amount);
            log.debug("Withdraw successful");
            return new TransactionResponse(accountID, account.getBalance(), "Withdraw successful");
        }
        else if(account != null) {
            log.warn("Insufficient funds available");
            return new TransactionResponse(accountID, account.getBalance(), "Insufficient funds available");
        }
        else{
            log.warn("Account not found");
            return new TransactionResponse(accountID, 0.0, "Account not found");
        }
    }

    public TransactionResponse checkBalance(String accountID){
        Account account = accounts.getOrDefault(accountID, null);
        if(account != null){
            return new TransactionResponse(accountID, account.getBalance(), "Check balance successful");
        }
        return new TransactionResponse(accountID, 0.0, "Account not found");
    }
}
