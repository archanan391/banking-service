package com.banking.service;

import com.banking.dto.TransactionResponse;
import com.banking.exception.AccountNotFoundException;
import com.banking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountServiceTest {

    private AccountService accountService;

    @BeforeEach
    void setup(){
        accountService = new AccountService();
    }

    @Test
    void createAccountSuccessful() {
        TransactionResponse response = accountService.create("Savings", "user123", 500.0);

        assertEquals(500.0, response.getBalance());
        assertEquals("Account Creation Successful", response.getMessage());
        assertNotNull(response.getAccountID());
    }

    @Test
    void createAccountWithZeroBalance() {
        TransactionResponse response = accountService.create("Checking", "user456", 0.0);

        assertEquals(0.0, response.getBalance());
        assertEquals("Account Creation Successful", response.getMessage());
        assertNotNull(response.getAccountID());
    }

    @Test
    void depositToNonexistentAccountThrowsException() {
        AccountNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.deposit("doesNotExist", 100.0)
        );
        assertEquals("Account with ID 'doesNotExist' not found", exception.getMessage());
    }

    @Test
    void depositToExistingAccountIncreasesBalance() {
        // First, create and add an account to the service's map
        String accountID = "acc100";
        accountService.accounts.put(accountID, new Account(accountID, 50.0, "Savings", "user1"));

        TransactionResponse response = accountService.deposit(accountID, 150.0);

        assertEquals(accountID, response.getAccountID());
        assertEquals(200.0, response.getBalance());
        assertEquals("Deposit successful", response.getMessage());
    }

    @Test
    void withdrawFromNonexistentAccountThrowsException() {
        AccountNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.withdraw("doesNotExist", 50.0)
        );
        assertEquals("Account with ID 'doesNotExist' not found", exception.getMessage());
    }

    @Test
    void withdrawFromExistingAccountDecreasesBalance() {
        String accountID = "acc200";
        accountService.accounts.put(accountID, new com.banking.model.Account(accountID, 300.0, "Checking", "user2"));

        TransactionResponse response = accountService.withdraw(accountID, 100.0);

        assertEquals(accountID, response.getAccountID());
        assertEquals(200.0, response.getBalance());
        assertEquals("Withdraw successful", response.getMessage());
    }

    @Test
    void testWithdrawInsufficientFunds() {
        String accountID = "acc123";
        accountService.accounts.put(accountID, new Account(accountID, 0.0, "Savings", "user1"));
        accountService.deposit("acc123", 30.0);
        TransactionResponse response = accountService.withdraw("acc123", 50.0);

        assertEquals("acc123", response.getAccountID());
        assertEquals(30.0, response.getBalance());
        assertEquals("Insufficient funds available", response.getMessage());
    }

    @Test
    void checkBalanceForNonexistentAccountThrowsException() {
        AccountNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                AccountNotFoundException.class,
                () -> accountService.checkBalance("doesNotExist")
        );
        assertEquals("Account with ID 'doesNotExist' not found", exception.getMessage());
    }

    @Test
    void checkBalanceForExistingAccountReturnsCorrectBalance() {
        String accountID = "acc300";
        accountService.accounts.put(accountID, new com.banking.model.Account(accountID, 120.0, "Savings", "user3"));

        TransactionResponse response = accountService.checkBalance(accountID);

        assertEquals(accountID, response.getAccountID());
        assertEquals(120.0, response.getBalance());
        assertEquals("Check balance successful", response.getMessage());
    }
}
