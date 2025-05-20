package com.banking.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Account {

    private final String accountID;

    private Double balance;

    private String userID;

    private final String accountType;

    public Account(String accountID, double balance, String accountType, String userID) {
        this.accountID = accountID;
        this.balance = balance;
        this.accountType = accountType;
        this.userID = userID;
    }
}
