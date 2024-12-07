// File: src/test/java/com/yashwant/gahlot/Wallet/controller/UserControllerTest.java

package com.yashwant.gahlot.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashwant.gahlot.Wallet.controller.testcases.TestCaseData;
import com.yashwant.gahlot.Wallet.controller.testcases.UserCreateTestCase;
import com.yashwant.gahlot.Wallet.controller.testcases.UserCreateTestCaseData;
import com.yashwant.gahlot.Wallet.entity.User;
import com.yashwant.gahlot.Wallet.service.UserService;
import com.yashwant.gahlot.Wallet.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserController.
 */
public class UserControllerTest extends BaseTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUpTest() {
        objectMapper = new ObjectMapper();
        // Initialize MockMvc with standalone setup
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * Provides test cases for creating a user.
     */
    static Stream<TestCaseData<User, User>> userCreateTestCasesProvider() {
        String filePath = "src/test/resources/testcases/userCreateTestCase.json";
        return TestDataLoader.loadTestCases(filePath, UserCreateTestCase.class, User.class, User.class);
    }

    /**
     * Tests the createUser endpoint with various scenarios.
     *
     * @param testData The test case data.
     */
    @ParameterizedTest(name = "Test Case {index}")
    @MethodSource("userCreateTestCasesProvider")
    @DisplayName("Test createUser endpoint")
    public void createUserTest(TestCaseData<User, User> testData) throws Exception {
        // Arrange
        User request = testData.getRequest();
        User expectedResponse = testData.getResponse();

       // when(userService.save(eq(request))).thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        // Verify interactions
        verify(userService).save(eq(request));

        // Reset mocks for next test case
        reset(userService);
    }

    // Additional tests for other endpoints (GET, PUT, DELETE) can be added here
}
