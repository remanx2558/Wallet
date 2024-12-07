package com.yashwant.gahlot.Wallet.controller.testcases;

import com.yashwant.gahlot.Wallet.entity.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserWalletTestCaseData {
    private final Wallet request;
    private final Wallet response;
}