package com.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionRequest {
    private String accountID;
    private Double amount;

    public TransactionRequest(String accountID, double amount) {
        this.accountID = accountID;
        this.amount = amount;
    }
}
