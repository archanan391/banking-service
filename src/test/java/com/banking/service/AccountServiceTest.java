package com.banking.service;

import com.banking.dto.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setup(){
        accountService = new AccountService();
    }

    @Test
    void testDeposit() {
        TransactionResponse response = accountService.deposit("acc123", 100.0);

        assertEquals("acc123", response.getAccountID());
        assertEquals(100.0, response.getBalance());
        assertEquals("Deposit successful", response.getMessage());
    }

    @Test
    void testWithdrawSuccessful() {
        accountService.deposit("acc123", 200.0);
        TransactionResponse response = accountService.withdraw("acc123", 50.0);

        assertEquals("acc123", response.getAccountID());
        assertEquals(150.0, response.getBalance());
        assertEquals("Withdraw successful", response.getMessage());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        accountService.deposit("acc123", 30.0);
        TransactionResponse response = accountService.withdraw("acc123", 50.0);

        assertEquals("acc123", response.getAccountID());
        assertEquals(30.0, response.getBalance());
        assertEquals("Insufficient funds available", response.getMessage());
    }

    @Test
    void testWithdrawNonexistentAccount() {
        TransactionResponse response = accountService.withdraw("nonexistent", 20.0);

        assertEquals("nonexistent", response.getAccountID());
        assertEquals(0.0, response.getBalance());
        assertEquals("Account not found", response.getMessage());
    }

    @Test
    void testCheckBalanceSuccessful() {
        accountService.deposit("acc123", 200.0);
        TransactionResponse response = accountService.checkBalance("acc123");

        assertEquals("acc123", response.getAccountID());
        assertEquals(200.0, response.getBalance());
        assertEquals("Check balance successful", response.getMessage());
    }

    @Test
    void testCheckBalanceNonexistentAccount() {
        TransactionResponse response = accountService.checkBalance("nonexistent");

        assertEquals("nonexistent", response.getAccountID());
        assertEquals(0.0, response.getBalance());
        assertEquals("Account not found", response.getMessage());
    }
}
