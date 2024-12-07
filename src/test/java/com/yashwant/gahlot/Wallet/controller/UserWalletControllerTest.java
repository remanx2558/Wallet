// File: src/test/java/com/yashwant/gahlot/Wallet/controller/UserWalletControllerTest.java

package com.yashwant.gahlot.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashwant.gahlot.Wallet.controller.testcases.TestCaseData;
import com.yashwant.gahlot.Wallet.controller.testcases.UserWalletTestCase;
import com.yashwant.gahlot.Wallet.controller.testcases.UserWalletTestCaseData;
import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.entity.Wallet;
import com.yashwant.gahlot.Wallet.service.UserService;
import com.yashwant.gahlot.Wallet.service.WalletService;
import com.yashwant.gahlot.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserWalletController.
 */
public class UserWalletControllerTest extends BaseTest {

    @Mock
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserWalletController userWalletController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUpTest() {
        objectMapper = new ObjectMapper();
        // Initialize MockMvc with standalone setup
        mockMvc = MockMvcBuilders.standaloneSetup(userWalletController).build();
    }

    /**
     * Provides test cases for creating a user wallet.
     */
    static Stream<TestCaseData<Wallet, Wallet>> userWalletTestCasesProvider() {
        String filePath = "src/test/resources/testcases/userWalletTestCase.json";
        return TestDataLoader.loadTestCases(filePath, UserWalletTestCase.class, Wallet.class, Wallet.class);
    }

    /**
     * Tests the createMerchantWallet endpoint with various scenarios.
     *
     * @param testData The test case data.
     */
    @ParameterizedTest(name = "Test Case {index}")
    @MethodSource("userWalletTestCasesProvider")
    @DisplayName("Test createMerchantWallet endpoint")
    public void createMerchantWalletTest(TestCaseData<Wallet, Wallet> testData) throws Exception {
        // Arrange
        Wallet request = testData.getRequest();
        Wallet expectedResponse = testData.getResponse();

        // Mock the walletService to do nothing on addWallet
        doNothing().when(walletService).addWallet(eq(request));

        // Mock the userService to return a list containing a User
        when(userService.findbyMobile(eq(request.getPhone()))).thenReturn(List.of(new User()));

        // Mock the userService to do nothing on save
        doNothing().when(userService).save(any());

        // Act & Assert
        mockMvc.perform(post("/merchant/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        // Verify interactions
        verify(walletService).addWallet(eq(request));
        verify(userService).findbyMobile(eq(request.getPhone()));
        verify(userService).save(any());

        // Reset mocks for next test case
        reset(walletService, userService);
    }

    /**
     * Tests the createCustomerWallet endpoint with various scenarios.
     *
     * @param testData The test case data.
     */
    @ParameterizedTest(name = "Test Case {index}")
    @MethodSource("userWalletTestCasesProvider")
    @DisplayName("Test createCustomerWallet endpoint")
    public void createCustomerWalletTest(TestCaseData<Wallet, Wallet> testData) throws Exception {
        // Arrange
        Wallet request = testData.getRequest();
        Wallet expectedResponse = testData.getResponse();

        // Mock the walletService to do nothing on addWallet
        doNothing().when(walletService).addWallet(eq(request));

        // Mock the userService to return a list containing a User
        when(userService.findbyMobile(eq(request.getPhone()))).thenReturn(List.of(new User()));

        // Mock the userService to do nothing on save
        doNothing().when(userService).save(any());

        // Act & Assert
        mockMvc.perform(post("/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        // Verify interactions
        verify(walletService).addWallet(eq(request));
        verify(userService).findbyMobile(eq(request.getPhone()));
        verify(userService).save(any());

        // Reset mocks for next test case
        reset(walletService, userService);
    }

    // Additional tests for other endpoints (PUT activate, etc.) can be added here
}
