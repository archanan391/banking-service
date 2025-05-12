package com.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionResponse {
    private String accountID;
    private Double balance;

    public TransactionResponse(String accountID, Double balance) {
        this.accountID = accountID;
        this.balance = balance;
    }
}
