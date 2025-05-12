package com.banking.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Account {

    private final String accountID;

   private Double balance;

    public Account(String accountID, double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }
}
