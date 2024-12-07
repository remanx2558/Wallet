// File: src/test/java/com/yashwant/gahlot/Wallet/controller/TestDataLoader.java

package com.yashwant.gahlot.Wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashwant.gahlot.Wallet.controller.testcases.TestCaseData;
import com.yashwant.gahlot.Wallet.controller.testcases.UserCreateTestCase;
import com.yashwant.gahlot.Wallet.controller.testcases.UserWalletTestCase;
import com.yashwant.gahlot.Wallet.controller.testcases.WalletAddTestCase;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Utility class for loading test data.
 */
@Slf4j
public class TestDataLoader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Loads test cases from a JSON file.
     *
     * @param filePath Path to the JSON test case file.
     * @param clazz    Class type of the test case.
     * @param <R>      Request type.
     * @param <S>      Response type.
     * @return Stream of TestCaseData objects.
     */
    public static <R, S> Stream<TestCaseData<R, S>> loadTestCases(String filePath, Class<?> clazz, Class<R> requestClass, Class<S> responseClass) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            Object testCase = objectMapper.readValue(content, clazz);
            Stream<?> jsonArrayStream;
            if (testCase instanceof WalletAddTestCase) {
                jsonArrayStream = ((WalletAddTestCase) testCase).getJsonArray().stream();
            } else if (testCase instanceof UserCreateTestCase) {
                jsonArrayStream = ((UserCreateTestCase) testCase).getJsonArray().stream();
            } else if (testCase instanceof UserWalletTestCase) {
                jsonArrayStream = ((UserWalletTestCase) testCase).getJsonArray().stream();
            } else {
                throw new IllegalArgumentException("Unsupported test case class: " + clazz.getName());
            }

            return jsonArrayStream.map(json -> {
                try {
                    String reqJson = "";
                    String resJson = "";
                    if (clazz.equals(WalletAddTestCase.class)) {
                        reqJson = ((WalletAddTestCase.JsonTestCase) json).getReq();
                        resJson = ((WalletAddTestCase.JsonTestCase) json).getRes();
                    } else if (clazz.equals(UserCreateTestCase.class)) {
                        reqJson = ((UserCreateTestCase.JsonTestCase) json).getReq();
                        resJson = ((UserCreateTestCase.JsonTestCase) json).getRes();
                    } else if (clazz.equals(UserWalletTestCase.class)) {
                        reqJson = ((UserWalletTestCase.JsonTestCase) json).getReq();
                        resJson = ((UserWalletTestCase.JsonTestCase) json).getRes();
                    }

                    R request = objectMapper.readValue(reqJson, requestClass);
                    S response = objectMapper.readValue(resJson, responseClass);
                    return new TestCaseData<>(request, response);
                } catch (Exception e) {
                    log.error("Error parsing test case JSON", e);
                    return null;
                }
            }).filter(data -> data != null);
        } catch (Exception e) {
            log.error("Failed to load test cases from {}", filePath, e);
            return Stream.empty();
        }
    }
}
