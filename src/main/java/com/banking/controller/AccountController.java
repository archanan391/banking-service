package com.banking.controller;

import com.banking.dto.TransactionResponse;
import com.banking.dto.TransactionRequest;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/accounts/{accountID}/deposit")
    public void deposit(@PathVariable String accountID, @RequestBody TransactionRequest request){
        accountService.deposit(accountID, request.getAmount());
    }

    @PostMapping("/accounts/{accountID}/withdraw")
    public void withdraw(@PathVariable String accountID, @RequestBody TransactionRequest request){
        accountService.withdraw(accountID, request.getAmount());
    }

    @GetMapping("accounts/{accountID}/balance")
    public ResponseEntity<TransactionResponse> checkBalance(@PathVariable String accountID){
        TransactionResponse response = null;
        Optional<Double> balance = accountService.checkBalance(accountID);
        if(balance.isPresent())
            response = new TransactionResponse(accountID, balance.get());
        return ResponseEntity.ok(response);
    }

}
