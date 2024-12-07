package com.yashwant.gahlot.Wallet.controller.testcases;

import com.yashwant.gahlot.Wallet.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateTestCaseData {
    private final User request;
    private final User response;
}