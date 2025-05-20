package com.banking.controller;

import com.banking.dto.CreateAccountRequest;
import com.banking.dto.TransactionResponse;
import com.banking.dto.TransactionRequest;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.ErrorResponse;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    TransactionResponse response = null;

    @PostMapping("/accounts")
    public ResponseEntity<TransactionResponse> create(@RequestBody CreateAccountRequest request){
        response = accountService.create(request.getAccountType(), request.getUserID(), request.getBalance());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountID}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountID, @RequestBody TransactionRequest request){
        try {
            response = accountService.deposit(accountID, request.getAmount());
            return ResponseEntity.ok(response);
        } catch(AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ACCOUNT_NOT_FOUND", ex.getMessage()));
        }
    }

    @PostMapping("/accounts/{accountID}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountID, @RequestBody TransactionRequest request){
        try {
            response = accountService.withdraw(accountID, request.getAmount());
            return ResponseEntity.ok(response);
        } catch(AccountNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ACCOUNT_NOT_FOUND", ex.getMessage()));
        }
    }

    @GetMapping("accounts/{accountID}/balance")
    public ResponseEntity<?> checkBalance(@PathVariable String accountID){
        try {
            response = accountService.checkBalance(accountID);
            return ResponseEntity.ok(response);
        } catch(AccountNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ACCOUNT_NOT_FOUND", ex.getMessage()));
        }
    }

}
