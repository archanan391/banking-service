package com.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionResponse {
    private String accountID;
    private Double balance;
    private String message;

    public TransactionResponse(String accountID, Double balance, String message) {
        this.accountID = accountID;
        this.balance = balance;
        this.message = message;
    }
}
