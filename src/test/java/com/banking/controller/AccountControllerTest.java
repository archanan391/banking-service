package com.banking.controller;

import com.banking.dto.TransactionRequest;
import com.banking.dto.TransactionResponse;
import com.banking.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    // ObjectMapper for converting to/from JSON
    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Test
    void shouldReturn201WhenAccountIsCreated() throws Exception {
        TransactionResponse response = new TransactionResponse("1234", 100.0, "Account Creation Successful");

        when(accountService.create(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/accounts")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "alice")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "name": "user1"
                    }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value("1234"))
                .andExpect(jsonPath("$.message").value("Account Creation Successful"));
    }


    @Test
    void testDeposit() throws Exception {
        TransactionRequest request = new TransactionRequest("acc123", 100.0);
        TransactionResponse response = new TransactionResponse("acc123", 100.0, "Deposit successful");

        when(accountService.deposit("acc123", 100.0)).thenReturn(response);

        mockMvc.perform(post("/accounts/acc123/deposit")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "alice")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value("acc123"))
                .andExpect(jsonPath("$.balance").value(100.0))
                .andExpect(jsonPath("$.message").value("Deposit successful"));
    }

    @Test
    void testWithdraw() throws Exception {
        TransactionRequest request = new TransactionRequest("acc123", 50.0);
        TransactionResponse response = new TransactionResponse("acc123", 50.0, "Withdraw successful");

        when(accountService.withdraw("acc123", 50.0)).thenReturn(response);

        mockMvc.perform(post("/accounts/acc123/withdraw")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "alice")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value("acc123"))
                .andExpect(jsonPath("$.balance").value(50.0))
                .andExpect(jsonPath("$.message").value("Withdraw successful"));
    }

    @Test
    void testCheckBalance() throws Exception {
        TransactionResponse response = new TransactionResponse("acc123", 150.0, "Check balance successful");

        when(accountService.checkBalance("acc123")).thenReturn(response);

        mockMvc.perform(get("/accounts/acc123/balance")
                        .with(jwt().jwt(jwt -> jwt.claim("sub", "alice"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountID").value("acc123"))
                .andExpect(jsonPath("$.balance").value(150.0));
    }

}
