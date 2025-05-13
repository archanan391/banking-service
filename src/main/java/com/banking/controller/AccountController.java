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
    TransactionResponse response = null;

    @PostMapping("/accounts/{accountID}/deposit")
    public ResponseEntity<TransactionResponse> deposit(@PathVariable String accountID, @RequestBody TransactionRequest request){
        response = accountService.deposit(accountID, request.getAmount());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountID}/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable String accountID, @RequestBody TransactionRequest request){
        response = accountService.withdraw(accountID, request.getAmount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("accounts/{accountID}/balance")
    public ResponseEntity<TransactionResponse> checkBalance(@PathVariable String accountID){
        response = accountService.checkBalance(accountID);
        return ResponseEntity.ok(response);
    }

}
