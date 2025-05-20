package com.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class CreateAccountRequest {

    private String userID;
    private String accountType;
    private Double balance;
}
