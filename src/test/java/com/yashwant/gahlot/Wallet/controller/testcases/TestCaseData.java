package com.yashwant.gahlot.Wallet.controller.testcases;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestCaseData<R, S> {
    private final R request;
    private final S response;
}
