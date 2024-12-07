// File: src/test/java/com/yashwant/gahlot/Wallet/controller/WalletControllerTest.java

package com.yashwant.gahlot.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashwant.gahlot.Wallet.controller.testcases.TestCaseData;
import com.yashwant.gahlot.Wallet.controller.testcases.WalletAddTestCase;
import com.yashwant.gahlot.Wallet.controller.testcases.WalletAddTestCaseData;
import com.yashwant.gahlot.Wallet.entity.Wallet;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for WalletController.
 */
public class WalletControllerTest extends BaseTest {

    @Mock
    private WalletService walletService;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletController walletController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUpTest() {
        objectMapper = new ObjectMapper();
        // Initialize MockMvc with standalone setup
        mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    /**
     * Provides test cases for adding a wallet.
     */
    static Stream<TestCaseData<Wallet, String>> walletAddTestCasesProvider() {
        String filePath = "src/test/resources/testcases/walletAddTestCase.json";
        return TestDataLoader.loadTestCases(filePath, WalletAddTestCase.class, Wallet.class, String.class);
    }

    /**
     * Tests the addWallet endpoint with various scenarios.
     *
     * @param testData The test case data.
     */
    @ParameterizedTest(name = "Test Case {index}")
    @MethodSource("walletAddTestCasesProvider")
    @DisplayName("Test addWallet endpoint")
    public void addWalletTest(TestCaseData<Wallet, String> testData) throws Exception {
        // Arrange
        Wallet request = testData.getRequest();
        String expectedResponse = testData.getResponse();

        List<Wallet> existingWallets = List.of(new Wallet()); // Simulate existing wallet

        if ("Wallet already exists".equals(expectedResponse)) {
            when(walletService.findByPhone(eq(request.getPhone()))).thenReturn(existingWallets);
        } else {
            when(walletService.findByPhone(eq(request.getPhone()))).thenReturn(List.of());
            doNothing().when(walletService).addWallet(eq(request));
        }

        // Act & Assert
        mockMvc.perform(post("/api/wallet/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        // Verify interactions
        verify(walletService).findByPhone(eq(request.getPhone()));
        if ("Wallet created".equals(expectedResponse)) {
            verify(walletService).addWallet(eq(request));
        } else {
            verify(walletService, never()).addWallet(any());
        }

        // Reset mocks for next test case
        reset(walletService);
    }

    // Additional tests for other endpoints (PUT, GET, DELETE) can be added here
}
