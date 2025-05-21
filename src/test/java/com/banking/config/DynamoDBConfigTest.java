package com.banking.config;

import com.banking.security.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(TestSecurityConfig.class)
public class DynamoDBConfigTest {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Test
    public void testConnection(){
        assertNotNull(dynamoDbClient);
    }
}
