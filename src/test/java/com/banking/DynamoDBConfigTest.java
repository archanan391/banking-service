package com.banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DynamoDBConfigTest {

    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Test
    public void testConnection(){
        assertNotNull(dynamoDbClient);
    }
}
