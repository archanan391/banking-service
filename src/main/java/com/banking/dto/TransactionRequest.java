package com.banking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransactionRequest {
    private String accountID;
    private Double amount;
}
