package com.banking.controller;

import com.banking.dto.CreateAccountRequest;
import com.banking.dto.TransactionResponse;
import com.banking.dto.TransactionRequest;
import com.banking.model.Account;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    TransactionResponse response = null;

    @GetMapping("/accounts/{accountID}")
    public ResponseEntity<Account> getAccountById(@PathVariable String accountID) {
        Account account = accountService.getAccountById(accountID);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/accounts")
    public ResponseEntity<TransactionResponse> create(@RequestBody CreateAccountRequest request){
        response = accountService.create(request.getAccountType(), request.getUserID(), request.getBalance());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountID}/deposit")
    public ResponseEntity<?> deposit(@PathVariable String accountID, @RequestBody TransactionRequest request){
        /* This is not needed as we have @RestControllerAdvice enabled that automatically handles exceptions
        try {
            response = accountService.deposit(accountID, request.getAmount());
            return ResponseEntity.ok(response);
        } catch(AccountNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("ACCOUNT_NOT_FOUND", ex.getMessage()));
        } */
        response = accountService.deposit(accountID, request.getAmount());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/accounts/{accountID}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable String accountID, @RequestBody TransactionRequest request){
        response = accountService.withdraw(accountID, request.getAmount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("accounts/{accountID}/balance")
    public ResponseEntity<?> checkBalance(@PathVariable String accountID){
        response = accountService.checkBalance(accountID);
        return ResponseEntity.ok(response);
    }

}
