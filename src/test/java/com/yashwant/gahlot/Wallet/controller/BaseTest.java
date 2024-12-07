// File: src/test/java/com/yashwant/gahlot/Wallet/controller/BaseTest.java

package com.yashwant.gahlot.Wallet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * BaseTest class that provides common configurations for all test classes.
 */
@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // Common utility methods can be added here if needed
}
